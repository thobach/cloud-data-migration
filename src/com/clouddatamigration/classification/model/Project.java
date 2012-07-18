package com.clouddatamigration.classification.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Project extends AbstractModel<Project> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent
	private String name;

	@Persistent
	private String description;

	@Persistent
	private String department;

	@Persistent
	private String url;

	@Persistent
	private Date created = new Date();

	@Persistent(defaultFetchGroup = "true", column = "User_id")
	private User user;

	@Persistent(defaultFetchGroup = "true", column = "CloudDataStore_id")
	private CloudDataStore cloudDataStore;

	@Persistent(table = "Project_has_CDMScenario", defaultFetchGroup = "true")
	@Join(column = "Project_id")
	@Element(column = "CDMScenario_id")
	private Set<CDMScenario> cdmScenarios = new HashSet<CDMScenario>();

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
		this.department = department;
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
		this.url = url;
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
		return cdmScenarios;
	}

	/**
	 * @param cdmScenarios
	 *            the cdmScenarios to set
	 */
	public void setCdmScenarios(Set<CDMScenario> cdmScenarios) {
		this.cdmScenarios = cdmScenarios;
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

}
