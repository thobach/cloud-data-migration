package com.clouddatamigration.classification.model;

import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true", table = "CDMScenario")
public class CDMScenario extends AbstractModel<CDMScenario> implements
		Comparable<CDMScenario> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent
	private String name;

	@Persistent
	@Column(jdbcType = "LONGVARCHAR")
	private String description;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent
	private Date created = new Date();

	@Persistent(mappedBy = "cdmScenarios")
	Set<Project> projects;

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
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(CDMScenario o) {
		return this.orderNumber - o.orderNumber;
	}
}
