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

@PersistenceCapable(detachable = "true", table = "CDMCriterion")
@Entity
public class CDMCriterion extends AbstractModel<CDMCriterion> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent(column = "selectionType")
	private SelectionType selectionType;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the selectionType
	 */
	public SelectionType getSelectionType() {
		return selectionType;
	}

	/**
	 * @param selectionType
	 *            the selectionType to set
	 */
	public void setSelectionType(SelectionType selectionType) {
		this.selectionType = selectionType;
	}

	public void setSelectionType(String selectionType) {
		if (selectionType != null && !selectionType.equals("N")) {
			setSelectionType(SelectionType.valueOf(selectionType));
		}
	}

	@Override
	public String toString() {
		return key;
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("name", getName());
		fieldValues.put("key", getKey());
		fieldValues.put("selectionType", getSelectionType().toString());
		fieldValues.put("orderNumber", String.valueOf(getOrderNumber()));
		return fieldValues;
	}

}
