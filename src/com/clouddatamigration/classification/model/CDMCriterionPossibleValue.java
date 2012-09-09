package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
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
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;

@PersistenceCapable(detachable = "true", table = "CDMCriterionPossibleValue")
@Entity
public class CDMCriterionPossibleValue extends
		AbstractModel<CDMCriterionPossibleValue> implements
		Comparable<CDMCriterionPossibleValue> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent(defaultFetchGroup = "true", column = "CDMCriterion_id")
	private CDMCriterion cdmCriterion;

	@Persistent(table = "CDMScenarioProperty", defaultFetchGroup = "true")
	@Join(column = "CDMCriterionPossibleValue_id")
	@Element(column = "CDMScenario_id")
	private Set<CDMScenario> cdmScenarios = new TreeSet<CDMScenario>();

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
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
		this.name = name;
	}

	/**
	 * @return the orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		setOrderNumber(Integer.valueOf(orderNumber));
	}

	/**
	 * @return the cdmCriterion
	 */
	public CDMCriterion getCdmCriterion() {
		return cdmCriterion;
	}

	/**
	 * @param cdmCriterion
	 *            the cdmCriterion to set
	 */
	public void setCdmCriterion(CDMCriterion cdmCriterion) {
		this.cdmCriterion = cdmCriterion;
	}

	public void setCdmCriterion(String cdmCriterion) {
		setCdmCriterion(new CDMCriterion().findByID(cdmCriterion));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the cdmScenarios
	 */
	public Set<CDMScenario> getCdmScenarios() {
		return new TreeSet<CDMScenario>(cdmScenarios);
	}

	/**
	 * Returns all possible values of the given criterion
	 * 
	 * @param cdmCriterionId
	 * @return
	 */
	public Collection<CDMCriterionPossibleValue> getPossibleValues(
			String cdmCriterionId) {
		if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(CDMCriterionPossibleValue.class,
						"cdmCriterion == cdmCriterionParameter");
				query.declareParameters("String cdmCriterionParameter");
				query.setOrdering("orderNumber ASC");
				@SuppressWarnings("unchecked")
				Collection<CDMCriterionPossibleValue> possibleValues = (Collection<CDMCriterionPossibleValue>) query
						.execute(cdmCriterionId);
				possibleValues = pm.detachCopyAll(possibleValues);
				tx.commit();
				return possibleValues;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {
			SelectRequest selectRequest = new SelectRequest("SELECT * FROM `"
					+ PREFIX + persistentClass.getSimpleName()
					+ "` WHERE CDMCriterion_id = '" + cdmCriterionId + "'");
			Collection<CDMCriterionPossibleValue> items = new ArrayList<CDMCriterionPossibleValue>();
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
		return key + " - " + cdmCriterion.getKey();
	}

	@Override
	public int compareTo(CDMCriterionPossibleValue o) {
		if (this.cdmCriterion.getId().equals(o.getCdmCriterion().getId())) {
			return this.orderNumber - o.orderNumber;
		} else {
			return this.cdmCriterion.getOrderNumber()
					- o.getCdmCriterion().getOrderNumber();
		}
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("name", getName());
		fieldValues.put("key", getKey());
		fieldValues.put("cdmCriterion", getCdmCriterion().getId());
		fieldValues.put("orderNumber", String.valueOf(getOrderNumber()));
		return fieldValues;
	}

}
