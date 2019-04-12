
package es.deusto.spq.server.data.jdo;


import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public abstract class User {

	@PrimaryKey
	private String userID;
	@Persistent(defaultFetchGroup="true")
	private String name;
	@Persistent(defaultFetchGroup="true")
	private String email;
	@Persistent(defaultFetchGroup="true")
	private String password;
	
	/*@Persistent(defaultFetchGroup="true", mappedBy="user", dependentElement = "true")
	private List<Review> reviews;*/
	
	public User(String userID, String name, String email, String password, String address) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
		//reviews = new ArrayList<Review>();
	}
	
	public User(String userID, String name) {
		super();
		this.userID = userID;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
		
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
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
