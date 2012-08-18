package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(detachable = "true", table = "CDHSCriterionPossibleValue")
@Entity
public class CDHSCriterionPossibleValue extends
		AbstractModel<CDHSCriterionPossibleValue> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(jdbcType = "VARCHAR", length = 32)
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent
	private Type type;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent
	private Scale scale;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterion_id")
	@Unowned
	private CDHSCriterion cdhsCriterion;

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
	 * @return the type
	 */
	@Enumerated(EnumType.STRING)
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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

	/**
	 * @return the scale
	 */
	@Enumerated(EnumType.STRING)
	public Scale getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScale(Scale scale) {
		this.scale = scale;
	}

	/**
	 * @return the id
	 */
	@Id
	public String getId() {
		return id;
	}

	/**
	 * @return the cdhsCriterion
	 */
	public CDHSCriterion getCdhsCriterion() {
		return cdhsCriterion;
	}

	/**
	 * @param cdhsCriterion
	 *            the cdhsCriterion to set
	 */
	public void setCdhsCriterion(CDHSCriterion cdhsCriterion) {
		this.cdhsCriterion = cdhsCriterion;
	}

	/**
	 * Returns all possible values of the given criterion
	 * 
	 * @param cdhsCriterionId
	 * @return
	 */
	public Collection<CDHSCriterionPossibleValue> getPossibleValues(
			String cdhsCriterionId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(CDHSCriterionPossibleValue.class,
					"cdhsCriterion == cdhsCriterionParameter");
			query.declareParameters("String cdhsCriterionParameter");
			query.setOrdering("orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<CDHSCriterionPossibleValue> possibleValues = (Collection<CDHSCriterionPossibleValue>) query
					.execute(cdhsCriterionId);
			possibleValues = pm.detachCopyAll(possibleValues);
			tx.commit();
			return possibleValues;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Returns the possible values for all cloud data hosting solutions grouped
	 * by criterion, sorted by category
	 * 
	 * @return
	 */
	public Map<String, ArrayList<CDHSCriterionPossibleValue>> findAllGrouped() {
		Map<String, ArrayList<CDHSCriterionPossibleValue>> cdhsCriterionPossibleValueMap = new LinkedHashMap<String, ArrayList<CDHSCriterionPossibleValue>>();
		if (useJpa) {
			javax.persistence.Query query = em
					.createQuery("select o from "
							+ persistentClass.getName()
							+ " o where o.orderNumber is not null order by o.orderNumber ASC");
			// o.cdhsCriterion.cdhsCategory.orderNumber is not null &&
			// o.cdhsCriterion.orderNumber is not null &&
			// o.cdhsCriterion.cdhsCategory.orderNumber ASC,
			// o.cdhsCriterion.orderNumber ASC,
			@SuppressWarnings("unchecked")
			List<CDHSCriterionPossibleValue> obs = (List<CDHSCriterionPossibleValue>) query
					.getResultList();
			if (obs.size() > 0) {
				for (CDHSCriterionPossibleValue cdhsCriterionPossibleValue : obs) {
					if (cdhsCriterionPossibleValueMap
							.containsKey(cdhsCriterionPossibleValue
									.getCdhsCriterion().getKey())) {
						cdhsCriterionPossibleValueMap.get(
								cdhsCriterionPossibleValue.getCdhsCriterion()
										.getKey()).add(
								cdhsCriterionPossibleValue);
					} else {
						ArrayList<CDHSCriterionPossibleValue> properties = new ArrayList<CDHSCriterionPossibleValue>();
						properties.add(cdhsCriterionPossibleValue);
						cdhsCriterionPossibleValueMap.put(
								cdhsCriterionPossibleValue.getCdhsCriterion()
										.getKey(), properties);
					}
				}
			} else {
				return cdhsCriterionPossibleValueMap;
			}
			return cdhsCriterionPossibleValueMap;
		} else {
			PersistenceManager pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(-1);
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(CDHSCriterionPossibleValue.class);
				if (useGAE) {
					query.setOrdering("orderNumber ASC");
				} else {
					query.setOrdering("cdhsCriterion.cdhsCategory.orderNumber ASC, cdhsCriterion.orderNumber ASC, orderNumber ASC");
				}
				@SuppressWarnings("unchecked")
				Collection<CDHSCriterionPossibleValue> cdhsCriterionPossibleValues = (Collection<CDHSCriterionPossibleValue>) query
						.execute();
				cdhsCriterionPossibleValues = pm
						.detachCopyAll(cdhsCriterionPossibleValues);

				for (CDHSCriterionPossibleValue cdhsCriterionPossibleValue : cdhsCriterionPossibleValues) {
					CDHSCriterion cdhsCriterion;

					// seems GAE does not detach the child objects
					if (useGAE) {
						cdhsCriterionPossibleValue = pm.detachCopy(cdhsCriterionPossibleValue);
						cdhsCriterion = pm
								.detachCopy(cdhsCriterionPossibleValue
										.getCdhsCriterion());
					} else {
						cdhsCriterion = cdhsCriterionPossibleValue
								.getCdhsCriterion();
					}

					if (cdhsCriterionPossibleValueMap.containsKey(cdhsCriterion
							.getKey())) {
						cdhsCriterionPossibleValueMap.get(
								cdhsCriterion.getKey()).add(
								cdhsCriterionPossibleValue);
					} else {
						ArrayList<CDHSCriterionPossibleValue> properties = new ArrayList<CDHSCriterionPossibleValue>();
						properties.add(cdhsCriterionPossibleValue);
						cdhsCriterionPossibleValueMap.put(
								cdhsCriterion.getKey(), properties);
					}
				}

				tx.commit();
				return cdhsCriterionPossibleValueMap;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		}
	}

	@Override
	public String toString() {
		return key + " - " + cdhsCriterion.getKey() + " - "
				+ cdhsCriterion.getCdhsCategory().getName();
	}

}
