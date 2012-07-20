package com.clouddatamigration.classification.model;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class CDHSCriterion extends AbstractModel<CDHSCriterion> {

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

	@Persistent(defaultFetchGroup = "true", column = "CDHSCategory_id")
	private CDHSCategory cdhsCategory;

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the cdhsCategory
	 */
	public CDHSCategory getCdhsCategory() {
		return cdhsCategory;
	}

	/**
	 * @param cdhsCategory
	 *            the cdhsCategory to set
	 */
	public void setCdhsCategory(CDHSCategory cdhsCategory) {
		this.cdhsCategory = cdhsCategory;
	}

	@Override
	public String toString() {
		return key + " - " + cdhsCategory.getName();
	}

}
