package com.clouddatamigration.classification.model;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(detachable = "true", table = "CDHSCriterion")
@Entity
public class CDHSCriterion extends AbstractModel<CDHSCriterion> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent
	@Column(jdbcType = "LONGVARCHAR")
	private String description;

	@Persistent(column = "selectionType")
	private SelectionType selectionType;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCategory_id")
	@Unowned
	private CDHSCategory cdhsCategory;

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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return key + " - " + cdhsCategory.getName();
	}

}
