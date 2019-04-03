package es.deusto.spq.server.data;

import java.util.ArrayList;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class User {
	
	@PrimaryKey
	private String userId;
	private String name, password, email, adress;
	private int phone;
	private UserType type;
	
	@Persistent(mappedBy="user", dependentElement="true")
	@Join
	private ArrayList<Reservation> reservations;
	
	public User(String userId, String name, String password, String email, String adress, int phone, UserType type) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.email = email;
		this.adress = adress;
		this.phone = phone;
		this.type = type;
		this.reservations = new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}
	
}
