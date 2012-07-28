package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true", table = "CDMStrategy")
public class CDMStrategy extends AbstractModel<CDMStrategy> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32, name = "id")
	private String id;

	@Persistent(defaultFetchGroup = "true", column = "Project_id")
	private Project project;

	@Persistent(defaultFetchGroup = "true", column = "CDMCriterionPossibleValue_id")
	private CDMCriterionPossibleValue cdmCriterionPossibleValue;

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
	 * @return the cdmCriterionPossibleValue
	 */
	public CDMCriterionPossibleValue getCdmCriterionPossibleValue() {
		return cdmCriterionPossibleValue;
	}

	/**
	 * @param cdmCriterionPossibleValue
	 *            the cdmCriterionPossibleValue to set
	 */
	public void setCdmCriterionPossibleValue(
			CDMCriterionPossibleValue cdmCriterionPossibleValue) {
		this.cdmCriterionPossibleValue = cdmCriterionPossibleValue;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the cloud data migration strategy connected to the project id
	 * 
	 * @param projectId
	 * @return
	 */
	public HashMap<String, ArrayList<CDMStrategy>> findAllByProject(
			String projectId) {
		HashMap<String, ArrayList<CDMStrategy>> cdmStrategySet = new LinkedHashMap<String, ArrayList<CDMStrategy>>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(CDMStrategy.class,
					"project == projectParameter");
			query.declareParameters("String projectParameter");
			query.setOrdering("cdmCriterionPossibleValue.cdmCriterion.orderNumber ASC, cdmCriterionPossibleValue.orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<CDMStrategy> cdmStrategies = (Collection<CDMStrategy>) query
					.execute(projectId);

			cdmStrategies = pm.detachCopyAll(cdmStrategies);

			for (CDMStrategy cdmStrategy : cdmStrategies) {

				if (cdmStrategySet.containsKey(cdmStrategy
						.getCdmCriterionPossibleValue().getCdmCriterion()
						.getKey())) {
					cdmStrategySet.get(
							cdmStrategy.getCdmCriterionPossibleValue()
									.getCdmCriterion().getKey()).add(
							cdmStrategy);
				} else {
					ArrayList<CDMStrategy> properties = new ArrayList<CDMStrategy>();
					properties.add(cdmStrategy);
					cdmStrategySet.put(cdmStrategy
							.getCdmCriterionPossibleValue().getCdmCriterion()
							.getKey(), properties);
				}
			}

			tx.commit();
			return cdmStrategySet;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public String toString() {
		return project.getName() + " - "
				+ cdmCriterionPossibleValue.getCdmCriterion().getKey() + " - "
				+ cdmCriterionPossibleValue.getKey();
	}

}