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

@PersistenceCapable(detachable = "true")
public class CloudDataStoreProperty extends
		AbstractModel<CloudDataStoreProperty> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent(defaultFetchGroup = "true", column = "CloudDataStore_id")
	private CloudDataStore cloudDataStore;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterionPossibleValue_id")
	private CDHSCriterionPossibleValue cdhsCriterionPossibleValue;

	@Persistent
	private String inputValue;

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
	 * @return the input value
	 */
	public String getInputValue() {
		return inputValue;
	}

	/**
	 * @param inputValue
	 *            the input value to set
	 */
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	/**
	 * Returns the cloud data hosting solutions connected to the cloud data
	 * store id
	 * 
	 * @param cdsId
	 * @return
	 */
	public HashMap<String, ArrayList<CloudDataStoreProperty>> findAllByCDS(
			String cdsId) {
		HashMap<String, ArrayList<CloudDataStoreProperty>> cloudDataStorePropertySet = new LinkedHashMap<String, ArrayList<CloudDataStoreProperty>>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(CloudDataStoreProperty.class,
					"cloudDataStore == cloudDataStoreParameter");
			query.declareParameters("String cloudDataStoreParameter");
			query.setOrdering("cdhsCriterionPossibleValue.cdhsCriterion.cdhsCategory.orderNumber ASC, cdhsCriterionPossibleValue.cdhsCriterion.orderNumber ASC, cdhsCriterionPossibleValue.orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<CloudDataStoreProperty> cloudDataStoreProperties = (Collection<CloudDataStoreProperty>) query
					.execute(cdsId);

			cloudDataStoreProperties = pm
					.detachCopyAll(cloudDataStoreProperties);

			for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStoreProperties) {
				CDHSCriterionPossibleValue cdhsCriterionPossibleValue = new CDHSCriterionPossibleValue();
				cdhsCriterionPossibleValue = cdhsCriterionPossibleValue
						.findByID(cloudDataStoreProperty
								.getCdhsCriterionPossibleValue().getId());

				if (cloudDataStorePropertySet
						.containsKey(cdhsCriterionPossibleValue
								.getCdhsCriterion().getKey())) {
					cloudDataStorePropertySet.get(
							cdhsCriterionPossibleValue.getCdhsCriterion()
									.getKey()).add(cloudDataStoreProperty);
				} else {
					ArrayList<CloudDataStoreProperty> properties = new ArrayList<CloudDataStoreProperty>();
					properties.add(cloudDataStoreProperty);
					cloudDataStorePropertySet.put(cdhsCriterionPossibleValue
							.getCdhsCriterion().getKey(), properties);
				}
			}

			tx.commit();
			return cloudDataStorePropertySet;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

}