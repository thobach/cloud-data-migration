package com.clouddatamigration.classification.model;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class LocalDBLCriterionPossibleValue extends
		AbstractModel<LocalDBLCriterionPossibleValue> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent
	private int orderNumber;

	@Persistent(defaultFetchGroup = "true", column = "LocalDBLCriterion_id")
	private LocalDBLCriterion localDBLCriterion;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the localDBLCriterion
	 */
	public LocalDBLCriterion getLocalDBLCriterion() {
		return localDBLCriterion;
	}

	/**
	 * @param localDBLCriterion
	 *            the localDBLCriterion to set
	 */
	public void setLocalDBLCriterion(LocalDBLCriterion localDBLCriterion) {
		this.localDBLCriterion = localDBLCriterion;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns all possible values of the given criterion
	 * 
	 * @param localDBLCriterionParameterId
	 * @return
	 */
	public Collection<LocalDBLCriterionPossibleValue> getPossibleValues(
			String localDBLCriterionParameterId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(LocalDBLCriterionPossibleValue.class,
					"localDBLCriterion == localDBLCriterionParameter");
			query.declareParameters("String localDBLCriterionParameter");
			query.setOrdering("orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<LocalDBLCriterionPossibleValue> possibleValues = (Collection<LocalDBLCriterionPossibleValue>) query
					.execute(localDBLCriterionParameterId);
			possibleValues = pm.detachCopyAll(possibleValues);
			tx.commit();
			return possibleValues;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public String toString() {
		return key + " - " + localDBLCriterion.getName();
	}

}
