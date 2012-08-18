package com.clouddatamigration.store.model;

import java.util.Date;

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

import com.clouddatamigration.classification.model.AbstractModel;

@PersistenceCapable(detachable = "true")
@Entity
public class CloudDataStore extends AbstractModel<CloudDataStore> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String name;

	@Persistent
	private String provider;

	@Persistent
	private String website;

	@Persistent
	@Column(jdbcType = "LONGVARCHAR")
	private String description;

	@Persistent
	private Date created = new Date();

	@Persistent
	private Date updated;

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
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
		this.name = Jsoup.clean(name, Whitelist.none());
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = Jsoup.clean(provider, Whitelist.none());
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = Jsoup.clean(website, Whitelist.none());
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
		this.description = Jsoup.clean(description, Whitelist.none());
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	@Override
	public CloudDataStore save(CloudDataStore cds) {
		if (cds.getId() != null) {
			updated = new Date();
		}
		return super.save(cds);
	}

	@Override
	public String toString() {
		return name;
	}

}
