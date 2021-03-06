package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

@PersistenceCapable(detachable = "true", table = "LocalDBLCriterionPossibleValue")
@Entity
public class LocalDBLCriterionPossibleValue extends
		AbstractModel<LocalDBLCriterionPossibleValue> {

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

	@Persistent(defaultFetchGroup = "true", column = "LocalDBLCriterion_id")
	private LocalDBLCriterion localDBLCriterion;

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
	 * @return the localDBLCriterion
	 */
	public LocalDBLCriterion getLocalDBLCriterion() {
		return localDBLCriterion;
	}

	/**
	 * @param localDBLCriterion
	 *            the localDBLCriterion to set
	 */
	public void setLocalDBLCriterion(LocalDBLCriterion localDBLCriterion) {
		this.localDBLCriterion = localDBLCriterion;
	}

	public void setLocalDBLCriterion(String localDBLCriterion) {
		setLocalDBLCriterion(new LocalDBLCriterion()
				.findByID(localDBLCriterion));
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns all possible values of the given criterion
	 * 
	 * @param localDBLCriterionParameterId
	 * @return
	 */
	public Collection<LocalDBLCriterionPossibleValue> getPossibleValues(
			String localDBLCriterionParameterId) {
		if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(LocalDBLCriterionPossibleValue.class,
						"localDBLCriterion == localDBLCriterionParameter");
				query.declareParameters("String localDBLCriterionParameter");
				query.setOrdering("orderNumber ASC");
				@SuppressWarnings("unchecked")
				Collection<LocalDBLCriterionPossibleValue> possibleValues = (Collection<LocalDBLCriterionPossibleValue>) query
						.execute(localDBLCriterionParameterId);
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
					+ "` WHERE LocalDBLCriterion_id = '"
					+ localDBLCriterionParameterId + "'");
			Collection<LocalDBLCriterionPossibleValue> items = new ArrayList<LocalDBLCriterionPossibleValue>();
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
		return key + " - " + localDBLCriterion.getName();
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("name", getName());
		fieldValues.put("key", getKey());
		fieldValues.put("localDBLCriterion", getLocalDBLCriterion().getId());
		fieldValues.put("orderNumber", String.valueOf(getOrderNumber()));
		return fieldValues;
	}

}
