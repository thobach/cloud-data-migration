package com.clouddatamigration.classification.model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

@PersistenceCapable(detachable = "true", table = "CDMScenario")
@Entity
public class CDMScenario extends AbstractModel<CDMScenario> implements
		Comparable<CDMScenario> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String name;

	@Persistent
	@Column(jdbcType = "LONGVARCHAR")
	private String description;

	@Persistent
	private String imageLocation;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent
	private Date created = new Date();

	@Persistent(mappedBy = "cdmScenarios")
	private Set<Project> projects;

	@Persistent(table = "CDMScenarioProperty", defaultFetchGroup = "true")
	@Join(column = "CDMScenario_id")
	@Element(column = "CDMCriterionPossibleValue_id")
	private Set<CDMCriterionPossibleValue> cdmCriterionPossibleValues = new TreeSet<CDMCriterionPossibleValue>();

	@Persistent(table = "CDMScenarioImpact", defaultFetchGroup = "true")
	@Join(column = "CDMScenario_id")
	@Element(column = "Impact_id")
	private Set<Impact> cdmImpacts = new TreeSet<Impact>();

	/**
	 * @param id the id to set
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

	/**
	 * @return the projects
	 */
	public Set<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @return the cdmCriterionPossibleValues
	 */
	public Set<CDMCriterionPossibleValue> getCdmCriterionPossibleValues() {
		return new TreeSet<CDMCriterionPossibleValue>(
				cdmCriterionPossibleValues);
	}

	/**
	 * @param cdmCriterionPossibleValues
	 *            the cdmCriterionPossibleValues to set
	 */
	public void setCdmCriterionPossibleValues(
			Set<CDMCriterionPossibleValue> cdmCriterionPossibleValues) {
		this.cdmCriterionPossibleValues = cdmCriterionPossibleValues;
	}

	/**
	 * @return the cdmImpacts
	 */
	public Set<Impact> getCdmImpacts() {
		return new TreeSet<Impact>(cdmImpacts);
	}

	/**
	 * @param cdmImpacts
	 *            the cdmImpacts to set
	 */
	public void setCdmImpacts(Set<Impact> cdmImpacts) {
		this.cdmImpacts = cdmImpacts;
	}

	/**
	 * @return the imageLocation
	 */
	public String getImageLocation() {
		return imageLocation;
	}

	/**
	 * @param imageLocation
	 *            the imageLocation to set
	 */
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
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
