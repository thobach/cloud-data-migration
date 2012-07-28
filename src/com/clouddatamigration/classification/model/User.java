package com.clouddatamigration.classification.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.Cookie;

@PersistenceCapable(detachable = "true", table = "User")
public class User extends AbstractModel<User> {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.UUIDHEX)
	@Column(jdbcType = "VARCHAR", length = 32)
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
	}

	/**
	 * Returns the user by the given sessionToken or null if no user has that
	 * sessionToken
	 * 
	 * @param sessionToken
	 * @return
	 */
	public User findBySessionToken(String sessionToken) {
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

}
