package com.clouddatamigration.classification.model;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.NullValue;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.clouddatamigration.store.model.CloudDataStore;

@PersistenceCapable(detachable = "true", table = "Project")
public class Project extends AbstractModel<Project> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent(nullValue = NullValue.EXCEPTION)
	private String name;

	@Persistent
	@Column(jdbcType = "LONGVARCHAR")
	private String description;

	@Persistent
	private String department;

	@Persistent
	private String url;

	@Persistent
	private Date created = new Date();

	@Persistent
	private Date updated;

	@Persistent(defaultFetchGroup = "true", column = "User_id", nullValue = NullValue.EXCEPTION)
	private User user;

	@Persistent(defaultFetchGroup = "true", column = "CloudDataStore_id")
	private CloudDataStore cloudDataStore;

	@Persistent(table = "Project_has_CDMScenario", defaultFetchGroup = "true")
	@Join(column = "Project_id")
	@Element(column = "CDMScenario_id")
	private Set<CDMScenario> cdmScenarios = new TreeSet<CDMScenario>();

	@Persistent(table = "CDMStrategy", defaultFetchGroup = "true")
	@Join(column = "Project_id")
	@Element(column = "CDMCriterionPossibleValue_id")
	private Set<CDMCriterionPossibleValue> cdmCriterionPossibleValues = new TreeSet<CDMCriterionPossibleValue>();

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
		this.name = Jsoup.clean(name, Whitelist.none());
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
		this.description = Jsoup.clean(description, Whitelist.none());
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = Jsoup.clean(department, Whitelist.none());
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = Jsoup.clean(url, Whitelist.none());
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the cloudDataStore
	 */
	public CloudDataStore getCloudDataStore() {
		return cloudDataStore;
	}

	/**
	 * @param cloudDataStore
	 *            the cloudDataStore to set
	 */
	public void setCloudDataStore(CloudDataStore cloudDataStore) {
		this.cloudDataStore = cloudDataStore;
	}

	/**
	 * @return the cdmScenarios
	 */
	public Set<CDMScenario> getCdmScenarios() {
		return new TreeSet<CDMScenario>(cdmScenarios);
	}

	/**
	 * @param cdmScenarios
	 *            the cdmScenarios to set
	 */
	public void setCdmScenarios(Set<CDMScenario> cdmScenarios) {
		this.cdmScenarios = cdmScenarios;
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
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * Returns the projects connected to the userId
	 * 
	 * @param userId
	 * @return
	 */
	public Collection<Project> findAllByUser(String userId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(Project.class, "user == userParameter");
			query.declareParameters("String userParameter");
			@SuppressWarnings("unchecked")
			Collection<Project> projects = (Collection<Project>) query
					.execute(userId);
			projects = pm.detachCopyAll(projects);
			tx.commit();
			return projects;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Project save(Project project) {
		if (project.getId() != null) {
			updated = new Date();
		}
		return super.save(project);
	}

}
