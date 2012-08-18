package com.clouddatamigration.classification.model;

import java.util.Collection;
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

@PersistenceCapable(detachable = "true", table = "Impact")
@Entity
public class Impact extends AbstractModel<Impact> implements Comparable<Impact> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String key;

	@Persistent
	private String name;

	@Persistent
	@Column(jdbcType = "LONGVARCHAR")
	private String description;

	@Persistent(column = "orderNumber")
	private int orderNumber;

	@Persistent(defaultFetchGroup = "true", column = "ImpactCategory_id")
	private ImpactCategory impactCategory;

	@Persistent(table = "CDMScenarioImpact", defaultFetchGroup = "true")
	@Join(column = "Impact_id")
	@Element(column = "CDMScenario_id")
	private Set<CDMScenario> cdmScenarios = new TreeSet<CDMScenario>();

	/**
	 * @param id the id to set
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the impactCategory
	 */
	public ImpactCategory getImpactCategory() {
		return impactCategory;
	}

	/**
	 * @param impactCategory
	 *            the impactCategory to set
	 */
	public void setImpactCategory(ImpactCategory impactCategory) {
		this.impactCategory = impactCategory;
	}

	/**
	 * @param cdmScenarios
	 *            the cdmScenarios to set
	 */
	public void setCdmScenarios(Set<CDMScenario> cdmScenarios) {
		this.cdmScenarios = cdmScenarios;
	}

	/**
	 * Returns all possible values of the given criterion
	 * 
	 * @param cdmCriterionId
	 * @return
	 */
	public Collection<Impact> getPossibleValues(String cdmCriterionId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query query = pm.newQuery(Impact.class,
					"cdmCriterion == cdmCriterionParameter");
			query.declareParameters("String cdmCriterionParameter");
			query.setOrdering("orderNumber ASC");
			@SuppressWarnings("unchecked")
			Collection<Impact> possibleValues = (Collection<Impact>) query
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
	}

	@Override
	public String toString() {
		return key + " - " + impactCategory.getName();
	}

	@Override
	public int compareTo(Impact o) {
		if (this.impactCategory.getId().equals(o.getImpactCategory().getId())) {
			return this.orderNumber - o.orderNumber;
		} else {
			return this.impactCategory.getOrderNumber()
					- o.getImpactCategory().getOrderNumber();
		}
	}

}
