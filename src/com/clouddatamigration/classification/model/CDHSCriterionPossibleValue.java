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
public class CDHSCriterionPossibleValue extends
		AbstractModel<CDHSCriterionPossibleValue> {

	public enum Type {
		SELECT, INPUT
	}

	public enum Scale {
		NOT_COMPARABLE, UPPER_IS_BETTER, LOWER_IS_BETTER
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent
	private Type type;

	@Persistent
	private int orderNumber;

	@Persistent
	private Scale scale;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterion_id")
	private CDHSCriterion cdhsCriterion;

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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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
	 * @return the scale
	 */
	public Scale getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(Scale scale) {
		this.scale = scale;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the cdhsCriterion
	 */
	public CDHSCriterion getCdhsCriterion() {
		return cdhsCriterion;
	}

	/**
	 * @param cdhsCriterion
	 *            the cdhsCriterion to set
	 */
	public void setCdhsCriterion(CDHSCriterion cdhsCriterion) {
		this.cdhsCriterion = cdhsCriterion;
	}

	/**
	 * Returns all possible values of the given criterion
	 * 
	 * @param cdhsCriterionId
	 * @return
	 */
	public Collection<CDHSCriterionPossibleValue> getPossibleValues(
			String cdhsCriterionId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(CDHSCriterionPossibleValue.class,
					"cdhsCriterion == cdhsCriterionParameter");
			query.declareParameters("String cdhsCriterionParameter");
			query.setOrdering("orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<CDHSCriterionPossibleValue> possibleValues = (Collection<CDHSCriterionPossibleValue>) query
					.execute(cdhsCriterionId);
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

}
