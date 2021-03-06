package es.deusto.spq.server.data.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Guest extends User {

	@Persistent(defaultFetchGroup = "true")
	private String phone;
	@Persistent(defaultFetchGroup = "true")
	private String address;
	
	public Guest(String userID, String name, String email, String password, String phone, String address) {
		super(userID, name, email, password, address);
		this.phone = phone;
		this.address = address;
	}

	public Guest(String userID, String name) {
		super(userID, name);
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
	public String toString() {
		return "User [ID=" + this.getUserID() + ", name=" + this.getName() + ", email:" + this.getEmail() + ", phone="
				+ phone + ", address=" + address + "]";
	}

}
