package com.clouddatamigration.classification.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javax.persistence.Entity;
import javax.persistence.Id;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.clouddatamigration.store.model.CloudDataStore;

@PersistenceCapable(detachable = "true", table = "Project")
@Entity
public class Project extends AbstractModel<Project> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
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

	public void setUser(String user) {
		setUser(new User().findByID(user));
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

	public void setCloudDataStore(String cloudDataStore) {
		setCloudDataStore(new CloudDataStore().findByID(cloudDataStore));
	}

	/**
	 * @return the cdmScenarios
	 */
	public Set<CDMScenario> getCdmScenarios() {
		if (useSimpleDB) {
			SelectRequest selectRequest = new SelectRequest("SELECT * FROM `"
					+ PREFIX + "Project_has_CDMScenario"
					+ "` WHERE Project_id = '" + id + "'");
			Set<CDMScenario> items = new TreeSet<CDMScenario>();
			for (Item item : sdb.select(selectRequest).getItems()) {
				for (Attribute attribute : item.getAttributes()) {
					if (attribute.getName().equals("CDMScenario_id")) {
						items.add(new CDMScenario().findByID(attribute
								.getValue()));
					}
				}
			}
			return items;
		} else {
			return new TreeSet<CDMScenario>(cdmScenarios);
		}
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
		if (useSimpleDB) {
			SelectRequest selectRequest = new SelectRequest("SELECT * FROM `"
					+ PREFIX + "CDMStrategy" + "` WHERE Project_id = '" + id
					+ "'");
			Set<CDMCriterionPossibleValue> items = new TreeSet<CDMCriterionPossibleValue>();
			for (Item item : sdb.select(selectRequest).getItems()) {
				for (Attribute attribute : item.getAttributes()) {
					if (attribute.getName().equals(
							"CDMCriterionPossibleValue_id")) {
						items.add(new CDMCriterionPossibleValue()
								.findByID(attribute.getValue()));
					}
				}

			}
			return items;
		} else {
			return new TreeSet<CDMCriterionPossibleValue>(
					cdmCriterionPossibleValues);
		}
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

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setUpdated(String updated) {
		if (!"N".equals(updated)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				setUpdated(df.parse(updated));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	public void setCreated(String created) {
		if (!"N".equals(created)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				setCreated(df.parse(created));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the projects connected to the userId
	 * 
	 * @param userId
	 * @return
	 */
	public Collection<Project> findAllByUser(String userId) {
		if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(Project.class,
						"user == userParameter");
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
		} else if (useSimpleDB) {
			SelectRequest selectRequest = new SelectRequest("SELECT * FROM `"
					+ PREFIX + persistentClass.getSimpleName()
					+ "` WHERE User_id = '" + userId + "'");
			Collection<Project> items = new ArrayList<Project>();
			for (Item item : sdb.select(selectRequest).getItems()) {
				items.add(parseResultToObject(item.getAttributes()));
			}
			return items;
		} else {
			throw new RuntimeException("Datastore not set or supported.");
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

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("name", getName());
		fieldValues.put("cloudDataStore", getCloudDataStore().getId());
		fieldValues.put("created", String.valueOf(getCreated().getTime()));
		fieldValues.put("updated", String.valueOf(getUpdated().getTime()));
		fieldValues.put("description", getDescription());
		fieldValues.put("url", getUrl());
		fieldValues.put("user", getUser().getId());
		fieldValues.put("department", getDepartment());
		return fieldValues;
	}

}
