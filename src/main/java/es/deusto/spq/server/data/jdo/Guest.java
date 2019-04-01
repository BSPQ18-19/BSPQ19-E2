package es.deusto.spq.server.data.jdo;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Guest extends User {

	private int phone;
	private String address;
	
	public Guest(String userID, String name, String email, String password, int phone, String address) {
		super(userID, name, email, password, phone, address);
		this.phone = phone;
		this.address = address;
	}
	
	public int getPhone() {
		return phone;
	}
	
	public void setPhone(int phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [ID=" + this.getUserID() + ", name=" + this.getName() + ", email:" + this.getEmail() + ", phone="
				+ phone + ", address=" + address + "]";
	}

}
