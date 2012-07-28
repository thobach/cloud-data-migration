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

@PersistenceCapable(detachable = "true", table = "LocalDBLProperty")
public class LocalDBLProperty extends AbstractModel<LocalDBLProperty> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent(defaultFetchGroup = "true", column = "Project_id")
	private Project project;

	@Persistent(defaultFetchGroup = "true", column = "LocalDBLCriterionPossibleValue_id")
	private LocalDBLCriterionPossibleValue localDBLCriterionPossibleValue;

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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the localDBLCriterionPossibleValue
	 */
	public LocalDBLCriterionPossibleValue getLocalDBLCriterionPossibleValue() {
		return localDBLCriterionPossibleValue;
	}

	/**
	 * @param localDBLCriterionPossibleValue
	 *            the localDBLCriterionPossibleValue to set
	 */
	public void setLocalDBLCriterionPossibleValue(
			LocalDBLCriterionPossibleValue localDBLCriterionPossibleValue) {
		this.localDBLCriterionPossibleValue = localDBLCriterionPossibleValue;
	}

	/**
	 * Returns the cloud data migration strategy connected to the project id
	 * 
	 * @param projectId
	 * @return
	 */
	public HashMap<String, ArrayList<LocalDBLProperty>> findAllByProject(
			String projectId) {
		HashMap<String, ArrayList<LocalDBLProperty>> cdmStrategySet = new LinkedHashMap<String, ArrayList<LocalDBLProperty>>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(LocalDBLProperty.class,
					"project == projectParameter");
			query.declareParameters("String projectParameter");
			query.setOrdering("localDBLCriterionPossibleValue.localDBLCriterion.localDBLCategory.orderNumber ASC, localDBLCriterionPossibleValue.localDBLCriterion.orderNumber ASC, localDBLCriterionPossibleValue.orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<LocalDBLProperty> localDblProperties = (Collection<LocalDBLProperty>) query
					.execute(projectId);

			localDblProperties = pm.detachCopyAll(localDblProperties);

			for (LocalDBLProperty localDblProperty : localDblProperties) {

				if (cdmStrategySet.containsKey(localDblProperty
						.getLocalDBLCriterionPossibleValue()
						.getLocalDBLCriterion().getName())) {
					cdmStrategySet.get(
							localDblProperty
									.getLocalDBLCriterionPossibleValue()
									.getLocalDBLCriterion().getName()).add(
							localDblProperty);
				} else {
					ArrayList<LocalDBLProperty> properties = new ArrayList<LocalDBLProperty>();
					properties.add(localDblProperty);
					cdmStrategySet.put(localDblProperty
							.getLocalDBLCriterionPossibleValue()
							.getLocalDBLCriterion().getName(), properties);
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
		return project.getName()
				+ " - "
				+ localDBLCriterionPossibleValue.getLocalDBLCriterion()
						.getName() + " - "
				+ localDBLCriterionPossibleValue.getKey();
	}

}