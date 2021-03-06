package com.clouddatamigration.classification.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.clouddatamigration.store.model.CloudDataStoreProperty;

@PersistenceCapable(detachable = "true", table = "Solution")
@Entity
public class Solution extends AbstractModel<Solution> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String name;

	@Persistent
	private String description;

	@Persistent(defaultFetchGroup = "true", column = "LocalDBLCriterionPossibleV_id")
	LocalDBLCriterionPossibleValue localDBLCriterionPossibleValue;

	@Persistent(defaultFetchGroup = "true", column = "CDHSCriterionPossibleValue_id")
	CDHSCriterionPossibleValue cdhsCriterionPossibleValue;

	@Persistent(defaultFetchGroup = "true", column = "CDMCriterionPossibleValue_id")
	CDMCriterionPossibleValue cdmCriterionPossibleValue1;

	@Persistent(defaultFetchGroup = "true", column = "CDMCriterionPossibleValue_id1")
	CDMCriterionPossibleValue cdmCriterionPossibleValue2;

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
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

	public void setLocalDBLCriterionPossibleValue(
			String localDBLCriterionPossibleValue) {
		if (!"N".equals(localDBLCriterionPossibleValue)) {
			setLocalDBLCriterionPossibleValue(new LocalDBLCriterionPossibleValue()
					.findByID(localDBLCriterionPossibleValue));
		}
	}

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
		if (!"N".equals(cdhsCriterionPossibleValue)) {
			setCdhsCriterionPossibleValue(new CDHSCriterionPossibleValue()
					.findByID(cdhsCriterionPossibleValue));
		}
	}

	/**
	 * @return the cdmCriterionPossibleValue1
	 */
	public CDMCriterionPossibleValue getCdmCriterionPossibleValue1() {
		return cdmCriterionPossibleValue1;
	}

	/**
	 * @param cdmCriterionPossibleValue1
	 *            the cdmCriterionPossibleValue1 to set
	 */
	public void setCdmCriterionPossibleValue1(
			CDMCriterionPossibleValue cdmCriterionPossibleValue1) {
		this.cdmCriterionPossibleValue1 = cdmCriterionPossibleValue1;
	}

	public void setCdmCriterionPossibleValue1(String cdmCriterionPossibleValue1) {
		if (!"N".equals(cdmCriterionPossibleValue1)) {
			setCdmCriterionPossibleValue1(new CDMCriterionPossibleValue()
					.findByID(cdmCriterionPossibleValue1));
		}
	}

	/**
	 * @return the cdmCriterionPossibleValue2
	 */
	public CDMCriterionPossibleValue getCdmCriterionPossibleValue2() {
		return cdmCriterionPossibleValue2;
	}

	/**
	 * @param cdmCriterionPossibleValue2
	 *            the cdmCriterionPossibleValue2 to set
	 */
	public void setCdmCriterionPossibleValue2(
			CDMCriterionPossibleValue cdmCriterionPossibleValue2) {
		this.cdmCriterionPossibleValue2 = cdmCriterionPossibleValue2;
	}

	public void setCdmCriterionPossibleValue2(String cdmCriterionPossibleValue2) {
		if (!"N".equals(cdmCriterionPossibleValue2)) {
			setCdmCriterionPossibleValue2(new CDMCriterionPossibleValue()
					.findByID(cdmCriterionPossibleValue2));
		}
	}

	public List<Solution> getPossilbeSolutions(String projectId) {
		List<Solution> solutions = new ArrayList<Solution>();
		if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			pm.getFetchPlan().setMaxFetchDepth(-1);
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();

				Query query = pm.newQuery(Solution.class);
				@SuppressWarnings("unchecked")
				Collection<Solution> allSolutions = (Collection<Solution>) query
						.execute();
				allSolutions = pm.detachCopyAll(allSolutions);

				Project project = new Project();
				project = project.findByID(projectId);

				Set<CDMCriterionPossibleValue> cdmStrategies = project
						.getCdmCriterionPossibleValues();

				CloudDataStoreProperty cloudDataStorePropertyService = new CloudDataStoreProperty();
				HashMap<String, ArrayList<CloudDataStoreProperty>> cloudDataStoreProperties = null;
				if (project.getCloudDataStore() != null) {
					cloudDataStoreProperties = cloudDataStorePropertyService
							.findAllByCDS(project.getCloudDataStore().getId());
				}

				LocalDBLProperty localDBLPropertyService = new LocalDBLProperty();
				HashMap<String, ArrayList<LocalDBLProperty>> localDBLProperties = localDBLPropertyService
						.findAllByProject(project.getId());

				for (Solution solution : allSolutions) {
					// single possible value of CDM criterion selected
					if (solution.getCdmCriterionPossibleValue1() != null
							&& solution.getCdmCriterionPossibleValue2() == null
							&& solution.getCdhsCriterionPossibleValue() == null
							&& solution.getLocalDBLCriterionPossibleValue() == null) {
						for (CDMCriterionPossibleValue cdmCriterionPossibleValue : cdmStrategies) {
							if (solution.getCdmCriterionPossibleValue1()
									.getId()
									.equals(cdmCriterionPossibleValue.getId())) {
								solutions.add(solution);
							}
						}
					}
					// two conflicting possible values of the same or different
					// CDM
					// criterion selected
					else if (solution.getCdmCriterionPossibleValue1() != null
							&& solution.getCdmCriterionPossibleValue2() != null
							&& solution.getCdhsCriterionPossibleValue() == null
							&& solution.getLocalDBLCriterionPossibleValue() == null) {
						for (CDMCriterionPossibleValue cdmCriterionPossibleValue1 : cdmStrategies) {
							for (CDMCriterionPossibleValue cdmCriterionPossibleValue2 : cdmStrategies) {
								if (solution
										.getCdmCriterionPossibleValue1()
										.getId()
										.equals(cdmCriterionPossibleValue1
												.getId())
										&& solution
												.getCdmCriterionPossibleValue2()
												.getId()
												.equals(cdmCriterionPossibleValue2
														.getId())) {
									solutions.add(solution);
								}
							}
						}
					}
					// conflicting possible value of a CDM criterion and a cloud
					// data store's criterion selected
					else if (solution.getCdmCriterionPossibleValue1() != null
							&& solution.getCdmCriterionPossibleValue2() == null
							&& solution.getCdhsCriterionPossibleValue() != null
							&& solution.getLocalDBLCriterionPossibleValue() == null
							&& cloudDataStoreProperties != null) {
						for (CDMCriterionPossibleValue cdmCriterionPossibleValue1 : cdmStrategies) {
							for (ArrayList<CloudDataStoreProperty> cloudDataStorePropertyList : cloudDataStoreProperties
									.values()) {
								for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyList) {
									if (solution
											.getCdmCriterionPossibleValue1()
											.getId()
											.equals(cdmCriterionPossibleValue1
													.getId())
											&& solution
													.getCdhsCriterionPossibleValue()
													.getId()
													.equals(cloudDataStoreProperty
															.getCdhsCriterionPossibleValue()
															.getId())) {
										solutions.add(solution);
									}
								}
							}
						}
					} // single possible value of CDHS criterion selected
					else if (solution.getCdmCriterionPossibleValue1() == null
							&& solution.getCdmCriterionPossibleValue2() == null
							&& solution.getCdhsCriterionPossibleValue() != null
							&& solution.getLocalDBLCriterionPossibleValue() == null
							&& cloudDataStoreProperties != null) {
						for (ArrayList<CloudDataStoreProperty> cloudDataStorePropertyList : cloudDataStoreProperties
								.values()) {
							for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyList) {
								if (solution
										.getCdhsCriterionPossibleValue()
										.getId()
										.equals(cloudDataStoreProperty
												.getCdhsCriterionPossibleValue()
												.getId())) {
									solutions.add(solution);
								}
							}
						}
					}
					// single possible value of local DBL criterion selected
					else if (solution.getCdmCriterionPossibleValue1() == null
							&& solution.getCdmCriterionPossibleValue2() == null
							&& solution.getCdhsCriterionPossibleValue() == null
							&& solution.getLocalDBLCriterionPossibleValue() != null) {
						for (ArrayList<LocalDBLProperty> localDBLPropertyList : localDBLProperties
								.values()) {
							for (LocalDBLProperty localDBLProperty : localDBLPropertyList) {
								if (solution
										.getLocalDBLCriterionPossibleValue()
										.getId()
										.equals(localDBLProperty
												.getLocalDBLCriterionPossibleValue()
												.getId())) {
									solutions.add(solution);
								}
							}
						}
					}
					// conflicting possible value of a local DBL criterion and a
					// cloud
					// data store's criterion selected
					else if (solution.getCdmCriterionPossibleValue1() == null
							&& solution.getCdmCriterionPossibleValue2() == null
							&& solution.getCdhsCriterionPossibleValue() != null
							&& solution.getLocalDBLCriterionPossibleValue() != null
							&& cloudDataStoreProperties != null) {
						for (ArrayList<LocalDBLProperty> localDBLPropertyList : localDBLProperties
								.values()) {
							for (ArrayList<CloudDataStoreProperty> cloudDataStorePropertyList : cloudDataStoreProperties
									.values()) {
								for (LocalDBLProperty localDBLProperty : localDBLPropertyList) {
									for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyList) {
										if (solution
												.getLocalDBLCriterionPossibleValue()
												.getId()
												.equals(localDBLProperty
														.getLocalDBLCriterionPossibleValue()
														.getId())
												&& solution
														.getCdhsCriterionPossibleValue()
														.getId()
														.equals(cloudDataStoreProperty
																.getCdhsCriterionPossibleValue()
																.getId())) {
											solutions.add(solution);
										}
									}
								}
							}
						}
					}
				}
				tx.commit();
				return solutions;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {

			Collection<Solution> allSolutions = new Solution().findAll();

			Project project = new Project();
			project = project.findByID(projectId);

			Set<CDMCriterionPossibleValue> cdmStrategies = project
					.getCdmCriterionPossibleValues();

			CloudDataStoreProperty cloudDataStorePropertyService = new CloudDataStoreProperty();
			HashMap<String, ArrayList<CloudDataStoreProperty>> cloudDataStoreProperties = null;
			if (project.getCloudDataStore() != null) {
				cloudDataStoreProperties = cloudDataStorePropertyService
						.findAllByCDS(project.getCloudDataStore().getId());
			}

			LocalDBLProperty localDBLPropertyService = new LocalDBLProperty();
			HashMap<String, ArrayList<LocalDBLProperty>> localDBLProperties = localDBLPropertyService
					.findAllByProject(project.getId());

			for (Solution solution : allSolutions) {
				// single possible value of CDM criterion selected
				if (solution.getCdmCriterionPossibleValue1() != null
						&& solution.getCdmCriterionPossibleValue2() == null
						&& solution.getCdhsCriterionPossibleValue() == null
						&& solution.getLocalDBLCriterionPossibleValue() == null) {
					for (CDMCriterionPossibleValue cdmCriterionPossibleValue : cdmStrategies) {
						if (solution.getCdmCriterionPossibleValue1().getId()
								.equals(cdmCriterionPossibleValue.getId())) {
							solutions.add(solution);
						}
					}
				}
				// two conflicting possible values of the same or different
				// CDM
				// criterion selected
				else if (solution.getCdmCriterionPossibleValue1() != null
						&& solution.getCdmCriterionPossibleValue2() != null
						&& solution.getCdhsCriterionPossibleValue() == null
						&& solution.getLocalDBLCriterionPossibleValue() == null) {
					for (CDMCriterionPossibleValue cdmCriterionPossibleValue1 : cdmStrategies) {
						for (CDMCriterionPossibleValue cdmCriterionPossibleValue2 : cdmStrategies) {
							if (solution.getCdmCriterionPossibleValue1()
									.getId()
									.equals(cdmCriterionPossibleValue1.getId())
									&& solution
											.getCdmCriterionPossibleValue2()
											.getId()
											.equals(cdmCriterionPossibleValue2
													.getId())) {
								solutions.add(solution);
							}
						}
					}
				}
				// conflicting possible value of a CDM criterion and a cloud
				// data store's criterion selected
				else if (solution.getCdmCriterionPossibleValue1() != null
						&& solution.getCdmCriterionPossibleValue2() == null
						&& solution.getCdhsCriterionPossibleValue() != null
						&& solution.getLocalDBLCriterionPossibleValue() == null
						&& cloudDataStoreProperties != null) {
					for (CDMCriterionPossibleValue cdmCriterionPossibleValue1 : cdmStrategies) {
						for (ArrayList<CloudDataStoreProperty> cloudDataStorePropertyList : cloudDataStoreProperties
								.values()) {
							for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyList) {
								if (solution
										.getCdmCriterionPossibleValue1()
										.getId()
										.equals(cdmCriterionPossibleValue1
												.getId())
										&& solution
												.getCdhsCriterionPossibleValue()
												.getId()
												.equals(cloudDataStoreProperty
														.getCdhsCriterionPossibleValue()
														.getId())) {
									solutions.add(solution);
								}
							}
						}
					}
				} // single possible value of CDHS criterion selected
				else if (solution.getCdmCriterionPossibleValue1() == null
						&& solution.getCdmCriterionPossibleValue2() == null
						&& solution.getCdhsCriterionPossibleValue() != null
						&& solution.getLocalDBLCriterionPossibleValue() == null
						&& cloudDataStoreProperties != null) {
					for (ArrayList<CloudDataStoreProperty> cloudDataStorePropertyList : cloudDataStoreProperties
							.values()) {
						for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyList) {
							if (solution
									.getCdhsCriterionPossibleValue()
									.getId()
									.equals(cloudDataStoreProperty
											.getCdhsCriterionPossibleValue()
											.getId())) {
								solutions.add(solution);
							}
						}
					}
				}
				// single possible value of local DBL criterion selected
				else if (solution.getCdmCriterionPossibleValue1() == null
						&& solution.getCdmCriterionPossibleValue2() == null
						&& solution.getCdhsCriterionPossibleValue() == null
						&& solution.getLocalDBLCriterionPossibleValue() != null) {
					for (ArrayList<LocalDBLProperty> localDBLPropertyList : localDBLProperties
							.values()) {
						for (LocalDBLProperty localDBLProperty : localDBLPropertyList) {
							if (solution
									.getLocalDBLCriterionPossibleValue()
									.getId()
									.equals(localDBLProperty
											.getLocalDBLCriterionPossibleValue()
											.getId())) {
								solutions.add(solution);
							}
						}
					}
				}
				// conflicting possible value of a local DBL criterion and a
				// cloud
				// data store's criterion selected
				else if (solution.getCdmCriterionPossibleValue1() == null
						&& solution.getCdmCriterionPossibleValue2() == null
						&& solution.getCdhsCriterionPossibleValue() != null
						&& solution.getLocalDBLCriterionPossibleValue() != null
						&& cloudDataStoreProperties != null) {
					for (ArrayList<LocalDBLProperty> localDBLPropertyList : localDBLProperties
							.values()) {
						for (ArrayList<CloudDataStoreProperty> cloudDataStorePropertyList : cloudDataStoreProperties
								.values()) {
							for (LocalDBLProperty localDBLProperty : localDBLPropertyList) {
								for (CloudDataStoreProperty cloudDataStoreProperty : cloudDataStorePropertyList) {
									if (solution
											.getLocalDBLCriterionPossibleValue()
											.getId()
											.equals(localDBLProperty
													.getLocalDBLCriterionPossibleValue()
													.getId())
											&& solution
													.getCdhsCriterionPossibleValue()
													.getId()
													.equals(cloudDataStoreProperty
															.getCdhsCriterionPossibleValue()
															.getId())) {
										solutions.add(solution);
									}
								}
							}
						}
					}
				}
			}
			return solutions;
		} else {
			throw new RuntimeException("Datastore not set or supported.");
		}
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("description", getDescription());
		fieldValues.put("name", getName());
		return fieldValues;
	}
}
