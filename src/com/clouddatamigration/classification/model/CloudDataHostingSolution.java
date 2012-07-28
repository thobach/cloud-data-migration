package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
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

@PersistenceCapable(detachable = "true", table = "CloudDataHostingSolution")
public class CloudDataHostingSolution extends
		AbstractModel<CloudDataHostingSolution> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent(defaultFetchGroup = "true", column = "Project_id")
	private Project project;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterionPossibleValue_id")
	private CDHSCriterionPossibleValue cdhsCriterionPossibleValue;

	@Persistent
	private String value;

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
					cloudDataHostingSolutionsSet.put(cloudDataHostingSolution
							.getCdhsCriterionPossibleValue().getCdhsCriterion()
							.getKey(), properties);
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

}