package com.clouddatamigration.store.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.clouddatamigration.classification.model.AbstractModel;
import com.clouddatamigration.classification.model.CDHSCriterionPossibleValue;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(detachable = "true")
@Entity
public class CloudDataStoreProperty extends
		AbstractModel<CloudDataStoreProperty> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent(defaultFetchGroup = "true", column = "CloudDataStore_id")
	@Unowned
	private CloudDataStore cloudDataStore;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterionPossibleValue_id")
	@Unowned
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

	public void setCdhsCriterionPossibleValue(String cdhsCriterionPossibleValue) {
		setCdhsCriterionPossibleValue(new CDHSCriterionPossibleValue()
				.findByID(cdhsCriterionPossibleValue));
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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

	public void setCloudDataStore(String cloudDataStore) {
		setCloudDataStore(new CloudDataStore().findByID(cloudDataStore));
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
		this.inputValue = Jsoup.clean(inputValue, Whitelist.none());
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
		if (cdsId != null && !cdsId.isEmpty()) {
			if (useJpa) {
				javax.persistence.Query query = em
						.createQuery("select o from "
								+ persistentClass.getName()
								+ " o where o.cloudDataStore = :cloudDataStoreParameter");
				query.setParameter("cloudDataStoreParameter", cdsId);
				@SuppressWarnings("unchecked")
				List<CloudDataStoreProperty> obs = (List<CloudDataStoreProperty>) query
						.getResultList();
				if (obs.size() > 0) {
					for (CloudDataStoreProperty cloudDataStoreProperty : obs) {
						if (cloudDataStorePropertySet
								.containsKey(cloudDataStoreProperty
										.getCdhsCriterionPossibleValue()
										.getCdhsCriterion().getKey())) {
							cloudDataStorePropertySet.get(
									cloudDataStoreProperty
											.getCdhsCriterionPossibleValue()
											.getCdhsCriterion().getKey()).add(
									cloudDataStoreProperty);
						} else {
							ArrayList<CloudDataStoreProperty> properties = new ArrayList<CloudDataStoreProperty>();
							properties.add(cloudDataStoreProperty);
							cloudDataStorePropertySet.put(
									cloudDataStoreProperty
											.getCdhsCriterionPossibleValue()
											.getCdhsCriterion().getKey(),
									properties);
						}
					}
				}
			} else if (!useJpa && !useSimpleDB) {
				PersistenceManager pm = pmf.getPersistenceManager();
				pm.getFetchPlan().setMaxFetchDepth(-1);
				Transaction tx = pm.currentTransaction();
				try {
					tx.begin();
					Query query = pm.newQuery(CloudDataStoreProperty.class,
							"cloudDataStore == cloudDataStoreParameter");
					query.declareParameters("String cloudDataStoreParameter");
					if (!useGAE) {
						query.setOrdering("cdhsCriterionPossibleValue.cdhsCriterion.cdhsCategory.orderNumber ASC, cdhsCriterionPossibleValue.cdhsCriterion.orderNumber ASC, cdhsCriterionPossibleValue.orderNumber ASC");
					}
					@SuppressWarnings("unchecked")
					Collection<CloudDataStoreProperty> cloudDataStoreProperties = (Collection<CloudDataStoreProperty>) query
							.execute(cdsId);
					cloudDataStoreProperties = pm
							.detachCopyAll(cloudDataStoreProperties);
					for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStoreProperties) {
						if (cloudDataStorePropertySet
								.containsKey(cloudDataStoreProperty
										.getCdhsCriterionPossibleValue()
										.getCdhsCriterion().getKey())) {
							cloudDataStorePropertySet.get(
									cloudDataStoreProperty
											.getCdhsCriterionPossibleValue()
											.getCdhsCriterion().getKey()).add(
									cloudDataStoreProperty);
						} else {
							ArrayList<CloudDataStoreProperty> properties = new ArrayList<CloudDataStoreProperty>();
							properties.add(cloudDataStoreProperty);
							cloudDataStorePropertySet.put(
									cloudDataStoreProperty
											.getCdhsCriterionPossibleValue()
											.getCdhsCriterion().getKey(),
									properties);
						}
					}
					tx.commit();
				} finally {
					if (tx.isActive()) {
						tx.rollback();
					}
					pm.close();
				}
			} else if (useSimpleDB) {
				SelectRequest selectRequest = new SelectRequest(
						"SELECT * FROM `" + PREFIX
								+ persistentClass.getSimpleName()
								+ "` WHERE CloudDataStore_id = '" + cdsId + "'");
				List<CloudDataStoreProperty> items = new ArrayList<CloudDataStoreProperty>();
				for (Item item : sdb.select(selectRequest).getItems()) {
					items.add(parseResultToObject(item.getAttributes()));
				}

				for (CloudDataStoreProperty cloudDataStoreProperty : items) {
					if (cloudDataStorePropertySet
							.containsKey(cloudDataStoreProperty
									.getCdhsCriterionPossibleValue()
									.getCdhsCriterion().getKey())) {
						cloudDataStorePropertySet.get(
								cloudDataStoreProperty
										.getCdhsCriterionPossibleValue()
										.getCdhsCriterion().getKey()).add(
								cloudDataStoreProperty);
					} else {
						ArrayList<CloudDataStoreProperty> properties = new ArrayList<CloudDataStoreProperty>();
						properties.add(cloudDataStoreProperty);
						cloudDataStorePropertySet.put(cloudDataStoreProperty
								.getCdhsCriterionPossibleValue()
								.getCdhsCriterion().getKey(), properties);
					}
				}
			} else {
				throw new RuntimeException("Datastore not set or supported.");
			}
		}
		return cloudDataStorePropertySet;
	}

	/**
	 * Returns the cloud data hosting solutions connected to the cloud data
	 * store id
	 * 
	 * @param cdsId
	 * @return
	 */
	public Collection<CloudDataStoreProperty> findAllByCDSId(String cdsId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-3);
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
			tx.commit();
			return cloudDataStoreProperties;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public String toString() {
		return cloudDataStore.getName() + " - "
				+ cdhsCriterionPossibleValue.getCdhsCriterion().getKey()
				+ " - " + cdhsCriterionPossibleValue.getKey();
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("inputValue", getInputValue());
		fieldValues.put("CloudDataStore", getCloudDataStore().getId());
		fieldValues.put("CDHSCriterionPossibleValue",
				getCdhsCriterionPossibleValue().getId());
		return fieldValues;
	}

}