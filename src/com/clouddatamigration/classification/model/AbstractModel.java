package com.clouddatamigration.classification.model;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Persistent;
import javax.persistence.EntityManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;

public abstract class AbstractModel<T> {

	protected static final String PREFIX = "clouddatamigration-";
	protected static boolean useJpa = false;
	protected static boolean useGAE = false;
	protected static boolean useSimpleDB = true;
	private static EntityManagerFactoryImpl factory;
	protected static PersistenceManagerFactory pmf;
	protected static EntityManager em;
	protected static AmazonSimpleDBClient sdb;
	private static Map<String, Map<String, Object>> cacheById = new HashMap<String, Map<String, Object>>();
	private static Map<String, Collection<Object>> cacheList = new HashMap<String, Collection<Object>>();
	protected static AmazonS3Client s3;

	static {
		if (useJpa) {
			factory = new EntityManagerFactoryImpl("clouddatamigration", null);
			em = factory.createEntityManager();
		} else if (!useJpa && !useSimpleDB) {
			InputStream propertiesStream = Project.class
					.getResourceAsStream("/clouddatamigration.properties");
			pmf = JDOHelper.getPersistenceManagerFactory(propertiesStream);
		} else if (useSimpleDB) {
			InputStream propertiesStream = Project.class
					.getResourceAsStream("/clouddatamigration.properties");
			try {
				sdb = new AmazonSimpleDBClient(new PropertiesCredentials(
						propertiesStream));

				// reread stream
				propertiesStream = Project.class
						.getResourceAsStream("/clouddatamigration.properties");
				s3 = new AmazonS3Client(new PropertiesCredentials(
						propertiesStream));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// this type parameter is just there to get the class inside of methods
	protected Class<T> persistentClass;

	protected Logger logger = LogManager.getLogger("Persistence");

	@SuppressWarnings("unchecked")
	public AbstractModel() {
		try {
			this.persistentClass = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception e) {
			// cglib messes with the classes by enhacing them ;(
			this.persistentClass = (Class<T>) ((java.lang.reflect.ParameterizedType) ((Class<T>) getClass()
					.getGenericSuperclass()).getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
	}

	public abstract String getId();

	public abstract Map<String, String> getFieldValues();

	public T save(T object) {
		if (useJpa) {
			em.persist(object);
		} else if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				object = pm.makePersistent(object);
				object = pm.detachCopy(object);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {
			ArrayList<ReplaceableAttribute> attributes = new ArrayList<ReplaceableAttribute>();
			for (String name : getFieldValues().keySet()) {
				attributes.add(new ReplaceableAttribute(name, getFieldValues()
						.get(name), true));
			}
			PutAttributesRequest putAttributesRequest = new PutAttributesRequest(
					PREFIX + persistentClass.getSimpleName(), getId(),
					attributes);
			sdb.putAttributes(putAttributesRequest);
		}
		return object;
	}

	public T findByID(String id) {
		if (useJpa) {
			javax.persistence.Query query = em.createQuery("select o from "
					+ persistentClass.getName() + " o where o.id = :id");
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<T> obs = (List<T>) query.getResultList();
			return obs.get(0);
		} else if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(3);
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				T object = (T) pm.getObjectById(persistentClass, id);
				object = pm.detachCopy(object);
				tx.commit();
				return object;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {
			if (cacheById.containsKey(persistentClass.getSimpleName())) {
				Map<String, Object> idList = cacheById.get(persistentClass
						.getSimpleName());
				if (idList.containsKey(id)) {
					@SuppressWarnings("unchecked")
					T t = (T) idList.get(id);
					System.out.println("Cache Hit: " + t);
					return t;
				}
			}
			GetAttributesRequest getAttributesRequest = new GetAttributesRequest(
					PREFIX + persistentClass.getSimpleName(), id);
			GetAttributesResult attributesResult = sdb
					.getAttributes(getAttributesRequest);
			List<Attribute> attributes = attributesResult.getAttributes();
			T t = parseResultToObject(attributes);
			System.out.println("Retrieved object of type "
					+ persistentClass.getSimpleName() + " (" + t + ").");
			if (cacheById.containsKey(persistentClass.getSimpleName())) {
				Map<String, Object> idList = cacheById.get(persistentClass
						.getSimpleName());
				idList.put(id, t);
			} else {
				Map<String, Object> idList = new HashMap<String, Object>();
				idList.put(id, t);
				cacheById.put(persistentClass.getSimpleName(), idList);
			}
			return t;
		} else {
			throw new RuntimeException("Datastore not set or supported.");
		}
	}

	protected T parseResultToObject(List<Attribute> attributes) {
		T t = null;
		try {
			t = persistentClass.newInstance();
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[1];
			paramTypes[0] = String.class;
			for (Attribute attribute : attributes) {
				Class<T> aClass = persistentClass;
				String methodName = "set"
						+ attribute.getName().substring(0, 1).toUpperCase()
						+ attribute.getName().substring(1);
				for (Field field : aClass.getDeclaredFields()) {
					if (attribute.getName().equals(
							field.getAnnotation(Persistent.class).column())) {
						methodName = "set"
								+ field.getName().substring(0, 1).toUpperCase()
								+ field.getName().substring(1);
					}
				}
				Method m = null;
				try {
					m = aClass.getMethod(methodName, paramTypes);
				} catch (NoSuchMethodException nsme) {
					nsme.printStackTrace();
				}
				if (m != null) {
					try {
						if (attribute.getValue().startsWith("s3://")) {
							// download content from S3 using a custom link
							String[] bucketAndKey = attribute.getValue().split(
									"s3://")[1].split("/");
							S3ObjectInputStream is = s3.getObject(
									bucketAndKey[0].toLowerCase(), bucketAndKey[1])
									.getObjectContent();
							m.invoke(t,
									new Scanner(is, "UTF-8")
											.useDelimiter("\\A").next());
						} else {
							m.invoke(t, attribute.getValue());
						}
					} catch (IllegalAccessException iae) {
						iae.printStackTrace();
					} catch (InvocationTargetException ite) {
						ite.printStackTrace();
					}
				} else {
					System.out.println("Method " + methodName
							+ " does not exist for "
							+ persistentClass.getSimpleName() + ".");
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	public Collection<T> findAll(String order) {
		if (useJpa) {
			javax.persistence.Query query = em.createQuery("select o from "
					+ persistentClass.getName() + " o"
					+ (order != null ? " order by o." + order : ""));
			@SuppressWarnings("unchecked")
			List<T> obs = (List<T>) query.getResultList();
			return obs;
		} else if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(3);
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(persistentClass);
				if (order != null) {
					query.setOrdering(order);
				}
				@SuppressWarnings("unchecked")
				Collection<T> object = (Collection<T>) query.execute();
				object = pm.detachCopyAll(object);
				tx.commit();
				return object;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {
			if (cacheList.containsKey(persistentClass.getSimpleName())) {
				@SuppressWarnings("unchecked")
				Collection<T> list = (Collection<T>) cacheList
						.get(persistentClass.getSimpleName());
				System.out.println("Cache Hit on list "
						+ persistentClass.getSimpleName());
				return list;
			} else {
				SelectRequest selectRequest = new SelectRequest(
						"SELECT * FROM `" + PREFIX
								+ persistentClass.getSimpleName() + "`");
				List<T> items = new ArrayList<T>();
				for (Item item : sdb.select(selectRequest).getItems()) {
					items.add(parseResultToObject(item.getAttributes()));
				}
				System.out.println("Retrieved " + items.size()
						+ " objects of type " + persistentClass.getSimpleName()
						+ ".");
				@SuppressWarnings("unchecked")
				Collection<Object> list = (Collection<Object>) items;
				cacheList.put(persistentClass.getSimpleName(), list);
				return items;
			}
		} else {
			throw new RuntimeException("Datastore not set or supported.");
		}
	}

	public Collection<T> findAll() {
		return findAll(null);
	}

	public void delete(T object) {
		if (useJpa) {
			em.remove(object);
		} else {
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				pm.deletePersistent(object);
				tx.commit();
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		}
	}

}
