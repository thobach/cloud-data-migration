package com.clouddatamigration.classification.model;

import java.io.InputStream;
import java.util.Collection;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AbstractModel<T> {

	static {
		InputStream propertiesStream = Project.class
				.getResourceAsStream("/clouddatamigration.properties");
		pmf = JDOHelper.getPersistenceManagerFactory(propertiesStream);
	}

	// this type parameter is just there to get the class inside of methods
	protected final Class<T> persistentClass;

	protected static PersistenceManagerFactory pmf;

	protected Logger logger = LogManager.getLogger("Persistence");

	@SuppressWarnings("unchecked")
	public AbstractModel() {
		this.persistentClass = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T save(T object) {
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
		return object;
	}

	public T findByID(String id) {
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

	public Collection<T> findAll(String order) {
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

	public Collection<T> findAll() {
		return findAll(null);
	}

	public void delete(T object) {
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
