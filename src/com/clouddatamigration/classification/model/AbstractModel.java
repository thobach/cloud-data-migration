package com.clouddatamigration.classification.model;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.persistence.EntityManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;

public class AbstractModel<T> {

	protected static boolean useJpa = false;
	protected static boolean useGAE = false;
	private static EntityManagerFactoryImpl factory;
	protected static PersistenceManagerFactory pmf;
	protected static EntityManager em;

	static {
		if (useJpa) {
			factory = new EntityManagerFactoryImpl("clouddatamigration", null);
			em = factory.createEntityManager();
		} else {
			InputStream propertiesStream = Project.class
					.getResourceAsStream("/clouddatamigration.properties");
			pmf = JDOHelper.getPersistenceManagerFactory(propertiesStream);
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

	public T save(T object) {
		if (useJpa) {
			em.persist(object);
		} else {
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
		} else {
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
		}
	}

	public Collection<T> findAll(String order) {
		if (useJpa) {
			javax.persistence.Query query = em.createQuery("select o from "
					+ persistentClass.getName() + " o"
					+ (order != null ? " order by o." + order : ""));
			@SuppressWarnings("unchecked")
			List<T> obs = (List<T>) query.getResultList();
			return obs;
		} else {
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
