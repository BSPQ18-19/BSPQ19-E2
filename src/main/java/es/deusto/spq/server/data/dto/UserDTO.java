package es.deusto.spq.server.data.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userID;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String address;
	
	private boolean isGuest;
	
	public UserDTO(String userID, String name, String email, String password, String phone, String address, boolean isGuest) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.isGuest = isGuest;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof UserDTO) {
			UserDTO object = (UserDTO) o;
			return object.getUserID().equals(userID);
		}
		return false;
	}
	
}
