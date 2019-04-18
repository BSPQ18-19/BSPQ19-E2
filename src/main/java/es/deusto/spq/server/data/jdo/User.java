
package es.deusto.spq.server.data.jdo;


import java.io.Serializable;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public abstract class User {

	/** The ID of the user */
	@PrimaryKey
	private String userID = null;
	/** The user-name */
	@Persistent(defaultFetchGroup="true")
	private String name = null;
	/** The email of the user. */
	@Persistent(defaultFetchGroup="true")
	@Unique
	private String email = null;
	/** The password of the user. */
	@Persistent(defaultFetchGroup="true")
	private String password = null;
	
	/**
	 * Constructor. Creates a new instance of the class.
	 * @param userID The ID of the user.
	 * @param name The user-name.
	 * @param email The email of the user.
	 * @param password The password of the user.
	 * @param address The home address of the user.
	 */
	public User(String userID, String name, String email, String password, String address) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Constructor. Creates a new instance of the class.
	 * @param userID The ID of the user.
	 * @param name The user-name.
	 */
	public User(String userID, String name) {
		super();
		this.userID = userID;
		this.name = name;
	}
	
	/**
	 * Returns the name of the user.
	 * @return String containing the user-name.
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * Sets the name of the user.
	 * @param name A string with the name of the user.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Retrieves the email of the user.
	 * @return A string with the name of the user.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email of the user.
	 * @param email A string containing the email of the user.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Retrieves the password of the user.
	 * @return A string containing the password of the user.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password of the user.
	 * @param password A string containing the password of the user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Retrieves the ID of the user.
	 * @return A string containing the ID of the user.
	 */
	public String getUserID() {
		return userID;
	}
	
	@Override
	public abstract String toString();
	
	@Override
	public boolean equals(Object o) {
		//Two users are equal objects if and only if they have the same userID.
		if(o instanceof User) {
			User object = (User) o;
			return object.getUserID().equals(userID);
		}
		return false;
	}

}
