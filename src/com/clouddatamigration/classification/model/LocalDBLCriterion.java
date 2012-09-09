package com.clouddatamigration.classification.model;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

@PersistenceCapable(detachable = "true", table = "LocalDBLCriterion")
@Entity
public class LocalDBLCriterion extends AbstractModel<LocalDBLCriterion> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String name;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent(defaultFetchGroup = "true", column = "LocalDBLCategory_id")
	private LocalDBLCategory localDBLCategory;

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	
	public void setOrderNumber(String orderNumber) {
		setOrderNumber(Integer.valueOf(orderNumber));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the localDBLCategory
	 */
	public LocalDBLCategory getLocalDBLCategory() {
		return localDBLCategory;
	}

	/**
	 * @param localDBLCategory
	 *            the localDBLCategory to set
	 */
	public void setLocalDBLCategory(LocalDBLCategory localDBLCategory) {
		this.localDBLCategory = localDBLCategory;
	}
	
	public void setLocalDBLCategory(String localDBLCategory) {
		setLocalDBLCategory(new LocalDBLCategory().findByID(localDBLCategory));
	}

	@Override
	public String toString() {
		return name + " - " + localDBLCategory.getName();
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("name", getName());
		fieldValues.put("localDBLCategory", getLocalDBLCategory().getId());
		fieldValues.put("orderNumber", String.valueOf(getOrderNumber()));
		return fieldValues;
	}

}
