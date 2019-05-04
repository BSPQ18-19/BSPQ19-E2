package es.deusto.spq.server.data.jdo;

import java.util.List;

import javax.jdo.annotations.*;

import java.util.ArrayList;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
public class Administrator extends User {

	/** The list of hotels created by this administrator. */
	@Persistent(defaultFetchGroup="true")
	@Join
	private List<Hotel> createdHotels;

	public Administrator(String userId, String name, String email, String password, String address) {
		super(userId, name, email, password, address);
		createdHotels = new ArrayList<Hotel>();
	}
	
	public Administrator(String userId, String name) {
		super(userId, name);
		createdHotels = new ArrayList<Hotel>();
	}
	
	public List<Hotel> getCreatedHotels(){
		return createdHotels;
	}
	
	public void addCreatedHotel(Hotel createdHotel) {
		createdHotels.add(createdHotel);
	}

	@Override
	public String toString() {
		return "User [ID=" + this.getUserID() + ", name=" + this.getName() + ", email:" + this.getEmail() + 
				"numberHotelCreated=" + createdHotels.size();
	}
	
}
