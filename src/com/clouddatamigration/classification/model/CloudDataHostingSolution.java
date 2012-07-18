package com.clouddatamigration.classification.model;

import java.util.Collection;
import java.util.HashMap;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
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
	public HashMap<String, CloudDataHostingSolution> findAllByProject(
			String projectId) {
		HashMap<String, CloudDataHostingSolution> cloudDataHostingSolutionsSet = new HashMap<String, CloudDataHostingSolution>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(CloudDataHostingSolution.class,
					"project == projectParameter");
			query.declareParameters("String projectParameter");
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

				cloudDataHostingSolutionsSet.put(cdhsCriterionPossibleValue
						.getCdhsCriterion().getKey(), cloudDataHostingSolution);
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

}