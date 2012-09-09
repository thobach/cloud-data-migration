package com.clouddatamigration.classification.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import javax.servlet.http.Cookie;

import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;

@PersistenceCapable(detachable = "true", table = "User")
@Entity
public class User extends AbstractModel<User> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(jdbcType = "VARCHAR", length = 32)
	@Id
	private String id;

	@Persistent
	private String username;

	@Persistent(column = "passwordHash")
	private String passwordHash;

	@Persistent
	private String email;

	@Persistent(column = "sessionToken")
	private String sessionToken;

	@Persistent(column = "sessionExpiryDate")
	private Date sessionExpiryDate;

	@Persistent
	private boolean verified;

	@Persistent
	private Date created = new Date();

	@Persistent
	private Date updated;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash
	 *            the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setUpdated(String updated) {
		if (!"N".equals(updated)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				setUpdated(df.parse(updated));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param passwordHash
	 *            the passwordHash to set
	 */
	public void setPassword(String password) {
		this.passwordHash = hash(password + id);
	}

	private String hash(String string) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(string.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	public void setCreated(String created) {
		if (!"N".equals(created)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				setCreated(df.parse(created));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setVerified(String verified) {
		setVerified(Boolean.parseBoolean(verified));
	}

	/**
	 * @return the sessionToken
	 */
	public String getSessionToken() {
		return sessionToken;
	}

	/**
	 * @param sessionToken
	 *            the sessionToken to set
	 */
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	/**
	 * @return the sessionExpiryDate
	 */
	public Date getSessionExpiryDate() {
		return sessionExpiryDate;
	}

	/**
	 * @param sessionExpiryDate
	 *            the sessionExpiryDate to set
	 */
	public void setSessionExpiryDate(Date sessionExpiryDate) {
		this.sessionExpiryDate = sessionExpiryDate;
	}

	public void setSessionExpiryDate(String setSessionExpiryDate) {
		if (!"N".equals(setSessionExpiryDate)) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				setSessionExpiryDate(df.parse(setSessionExpiryDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * Checks the credentials and creates a session token
	 * 
	 * @param password
	 * @return true if successful, false if not
	 */
	public boolean login(String password) {
		if (this.getPasswordHash().equals(hash(password + id))) {
			this.setSessionToken(UUID.randomUUID().toString().replace("-", ""));
			this.setSessionExpiryDate(new Date(System.currentTimeMillis()
					+ 1000 * 60 * 60 * 8));
			this.save(this);
			return true;
		} else {
			logger.info("Password did not match.");
			return false;
		}
	}

	/**
	 * Returns the user by the given username or null if user does not exist
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		if (useJpa) {
			if (sessionToken != null && !sessionToken.isEmpty()) {
				javax.persistence.Query query = em.createQuery("select o from "
						+ persistentClass.getName()
						+ " o where o.username = :usernameParameter");
				query.setParameter("usernameParameter", username);
				@SuppressWarnings("unchecked")
				List<User> obs = (List<User>) query.getResultList();
				if (obs.size() > 0) {
					return obs.get(0);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else if (!useJpa && !useSimpleDB) {
			PersistenceManager pm = pmf.getPersistenceManager();
			Transaction tx = pm.currentTransaction();
			try {
				tx.begin();
				Query query = pm.newQuery(User.class,
						"username == usernameParameter");
				query.declareParameters("String usernameParameter");
				query.setUnique(true);
				query.setRange(0, 1);
				User user = (User) query.execute(username);
				user = pm.detachCopy(user);
				tx.commit();
				return user;
			} finally {
				if (tx.isActive()) {
					tx.rollback();
				}
				pm.close();
			}
		} else if (useSimpleDB) {
			SelectRequest selectRequest = new SelectRequest("SELECT * FROM `"
					+ PREFIX + persistentClass.getSimpleName()
					+ "` WHERE username = '" + username + "'");
			return parseResultToObject(sdb.select(selectRequest).getItems()
					.get(0).getAttributes());
		} else {
			throw new RuntimeException("Datastore not set or supported.");
		}
	}

	/**
	 * Returns the user by the given sessionToken or null if no user has that
	 * sessionToken
	 * 
	 * @param sessionToken
	 * @return
	 */
	public User findBySessionToken(String sessionToken) {
		if (sessionToken != null && !sessionToken.isEmpty()) {
			if (useJpa) {
				javax.persistence.Query query = em.createQuery("select o from "
						+ persistentClass.getName()
						+ " o where o.sessionToken = :sessionTokenParameter");
				query.setParameter("sessionTokenParameter", sessionToken);
				@SuppressWarnings("unchecked")
				List<User> obs = (List<User>) query.getResultList();
				if (obs.size() > 0) {
					return obs.get(0);
				} else {
					return null;
				}
			} else if (!useJpa && !useSimpleDB) {
				PersistenceManager pm = pmf.getPersistenceManager();
				Transaction tx = pm.currentTransaction();
				try {
					tx.begin();
					Query query = pm.newQuery(User.class,
							"sessionToken == sessionTokenParameter");
					query.declareParameters("String sessionTokenParameter");
					query.setUnique(true);
					query.setRange(0, 1);
					User user = (User) query.execute(sessionToken);
					user = pm.detachCopy(user);
					tx.commit();
					return user;
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
								+ "` WHERE sessionToken = '" + sessionToken
								+ "'");
				List<Item> items = sdb.select(selectRequest).getItems();
				if (items.size() == 1) {
					return parseResultToObject(items.get(0).getAttributes());
				} else {
					return null;
				}
			} else {
				throw new RuntimeException("Datastore not set or supported.");
			}
		} else {
			return null;
		}
	}

	/**
	 * Returns the session token from the cookies
	 * 
	 * @param cookies
	 * @return session token or null
	 */
	public String findSessionToken(Cookie[] cookies) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sessionToken")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return username;
	}

	@Override
	public User save(User user) {
		if (user.getId() != null) {
			updated = new Date();
		}
		return super.save(user);
	}

	@Override
	public Map<String, String> getFieldValues() {
		HashMap<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put("id", getId());
		fieldValues.put("username", getUsername());
		fieldValues.put("verified", String.valueOf(isVerified()));
		fieldValues.put("created", String.valueOf(getCreated().getTime()));
		fieldValues.put("updated", String.valueOf(getUpdated().getTime()));
		fieldValues.put("passwordHash", getPasswordHash());
		fieldValues.put("email", getEmail());
		fieldValues.put("sessionExpiryDate",
				String.valueOf(getSessionExpiryDate().getTime()));
		fieldValues.put("sessionToken", getSessionToken());
		return fieldValues;
	}

}
