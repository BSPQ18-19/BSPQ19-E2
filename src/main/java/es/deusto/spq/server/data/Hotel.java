package es.deusto.spq.server.data;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Hotel {
	
	@PrimaryKey
	private String hotelId;
	private String name, location;
	private String[] services;
	private LocalDateTime seasonStart, seasonEnding;
	private ArrayList<Room> rooms;
	
	@Persistent(mappedBy="hotel", dependentElement="true")
	@Join
	private ArrayList<Reservation> reservations;
	
	public Hotel(String hotelId, String name, String location, String[] services, LocalDateTime seasonStart,
			LocalDateTime seasonEnding) {
		
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
		this.services = services;
		this.seasonStart = seasonStart;
		this.seasonEnding = seasonEnding;
		this.rooms = new ArrayList<>();
		this.reservations = new ArrayList<>();
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[] getServices() {
		return services;
	}

	public void setServices(String[] services) {
		this.services = services;
	}

	public LocalDateTime getSeasonStart() {
		return seasonStart;
	}

	public void setSeasonStart(LocalDateTime seasonStart) {
		this.seasonStart = seasonStart;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	public LocalDateTime getSeasonEnding() {
		return seasonEnding;
	}

	public void setSeasonEnding(LocalDateTime seasonEnding) {
		this.seasonEnding = seasonEnding;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}	
}
