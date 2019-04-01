package es.deusto.spq.server.data.jdo;


import java.io.Serializable;

import javax.jdo.annotations.*;

@PersistenceCapable
public abstract class User implements Serializable {

	@PrimaryKey
	private String userID;
	private String name;
	private String email;
	private String password;
	
	public User(String userID, String name, String email, String password, int phone, String address) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
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
	
}
