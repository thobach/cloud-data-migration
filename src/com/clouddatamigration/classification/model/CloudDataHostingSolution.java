package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;

@PersistenceCapable(detachable = "true", table = "CloudDataHostingSolution")
@Entity
public class CloudDataHostingSolution extends
		AbstractModel<CloudDataHostingSolution> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent(defaultFetchGroup = "true", column = "Project_id")
	private Project project;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterionPossibleValue_id")
	private CDHSCriterionPossibleValue cdhsCriterionPossibleValue;

	@Persistent
	private String value;

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cdhsCriterionPossibleValue
	 */
	public CDHSCriterionPossibleValue getCdhsCriterionPossibleValue() {
		return cdhsCriterionPossibleValue;
	}

	/**
	 * @param cdhsCriterionPossibleValue
	 *            the cdhsCriterionPossibleValue to set
	 */
	public void setCdhsCriterionPossibleValue(
			CDHSCriterionPossibleValue cdhsCriterionPossibleValue) {
		this.cdhsCriterionPossibleValue = cdhsCriterionPossibleValue;
	}

	public void setCdhsCriterionPossibleValue(String cdhsCriterionPossibleValue) {
		setCdhsCriterionPossibleValue(new CDHSCriterionPossibleValue()
				.findByID(cdhsCriterionPossibleValue));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	public void setProject(String project) {
		setProject(new Project().findByID(project));
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Returns the cloud data hosting solutions connected to the projectId
	 * 
	 * @param projectId
	 * @return
	 */
	public Map<String, ArrayList<CloudDataHostingSolution>> findAllByProject(
			String projectId) {
		Map<String, ArrayList<CloudDataHostingSolution>> cloudDataHostingSolutionsSet = new LinkedHashMap<String, ArrayList<CloudDataHostingSolution>>();
		if (!useJpa && !useSimpleDB) {

			PersistenceManager pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(-1);
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(CloudDataHostingSolution.class,
						"project == projectParameter");
				query.declareParameters("String projectParameter");
				query.setOrdering("cdhsCriterionPossibleValue.cdhsCriterion.cdhsCategory.orderNumber ASC, cdhsCriterionPossibleValue.cdhsCriterion.orderNumber ASC, cdhsCriterionPossibleValue.orderNumber ASC");
				@SuppressWarnings("unchecked")
				Collection<CloudDataHostingSolution> cloudDataHostingSolutions = (Collection<CloudDataHostingSolution>) query
						.execute(projectId);
				cloudDataHostingSolutions = pm
						.detachCopyAll(cloudDataHostingSolutions);

				for (CloudDataHostingSolution cloudDataHostingSolution : cloudDataHostingSolutions) {
					CDHSCriterionPossibleValue cdhsCriterionPossibleValue = new CDHSCriterionPossibleValue();
					cdhsCriterionPossibleValue = cdhsCriterionPossibleValue
							.findByID(cloudDataHostingSolution
									.getCdhsCriterionPossibleValue().getId());

					if (cloudDataHostingSolutionsSet
							.containsKey(cloudDataHostingSolution
									.getCdhsCriterionPossibleValue()
									.getCdhsCriterion().getKey())) {
						cloudDataHostingSolutionsSet.get(
								cloudDataHostingSolution
										.getCdhsCriterionPossibleValue()
										.getCdhsCriterion().getKey()).add(
								cloudDataHostingSolution);
					} else {
						ArrayList<CloudDataHostingSolution> properties = new ArrayList<CloudDataHostingSolution>();
						properties.add(cloudDataHostingSolution);
						cloudDataHostingSolutionsSet.put(
								cloudDataHostingSolution
										.getCdhsCriterionPossibleValue()
										.getCdhsCriterion().getKey(),
								properties);
					}
				}

				tx.commit();
				return cloudDataHostingSolutionsSet;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {
			SelectRequest selectRequest = new SelectRequest("SELECT * FROM `"
					+ PREFIX + persistentClass.getSimpleName()
					+ "` WHERE Project_id = '" + projectId + "'");
			Collection<CloudDataHostingSolution> items = new ArrayList<CloudDataHostingSolution>();
			for (Item item : sdb.select(selectRequest).getItems()) {
				items.add(parseResultToObject(item.getAttributes()));
			}
			for (CloudDataHostingSolution cloudDataHostingSolution : items) {
				CDHSCriterionPossibleValue cdhsCriterionPossibleValue = new CDHSCriterionPossibleValue();
				cdhsCriterionPossibleValue = cdhsCriterionPossibleValue
						.findByID(cloudDataHostingSolution
								.getCdhsCriterionPossibleValue().getId());

				if (cloudDataHostingSolutionsSet
						.containsKey(cloudDataHostingSolution
								.getCdhsCriterionPossibleValue()
								.getCdhsCriterion().getKey())) {
					cloudDataHostingSolutionsSet.get(
							cloudDataHostingSolution
									.getCdhsCriterionPossibleValue()
									.getCdhsCriterion().getKey()).add(
							cloudDataHostingSolution);
				} else {
					ArrayList<CloudDataHostingSolution> properties = new ArrayList<CloudDataHostingSolution>();
					properties.add(cloudDataHostingSolution);
					cloudDataHostingSolutionsSet.put(cloudDataHostingSolution
							.getCdhsCriterionPossibleValue().getCdhsCriterion()
							.getKey(), properties);
				}
			}
			return cloudDataHostingSolutionsSet;
		} else {
			throw new RuntimeException("Datastore not set or supported.");
		}
	}

	/**
	 * Returns the cloud data hosting solutions connected to the projectId
	 * 
	 * @param projectId
	 * @return
	 */
	public Collection<CloudDataHostingSolution> findAllByProjectId(
			String projectId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-3);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(CloudDataHostingSolution.class,
					"project == projectParameter");
			query.declareParameters("String projectParameter");
			query.setOrdering("cdhsCriterionPossibleValue.cdhsCriterion.cdhsCategory.orderNumber ASC, cdhsCriterionPossibleValue.cdhsCriterion.orderNumber ASC, cdhsCriterionPossibleValue.orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<CloudDataHostingSolution> cloudDataHostingSolutions = (Collection<CloudDataHostingSolution>) query
					.execute(projectId);
			cloudDataHostingSolutions = pm
					.detachCopyAll(cloudDataHostingSolutions);
			tx.commit();
			return cloudDataHostingSolutions;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public String toString() {
		return cdhsCriterionPossibleValue.getCdhsCriterion().getCdhsCategory()
				.getName()
				+ " - "
				+ cdhsCriterionPossibleValue.getCdhsCriterion().getKey()
				+ " - "
				+ cdhsCriterionPossibleValue.getKey()
				+ " - "
				+ project.getName();
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("project", getProject().getId());
		fieldValues.put("value", getValue());
		return fieldValues;
	}

}