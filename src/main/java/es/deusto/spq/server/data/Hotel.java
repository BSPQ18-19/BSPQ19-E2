package es.deusto.spq.server.data;

import java.time.LocalDate;
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
	private LocalDate seasonStart, seasonEnding;
//	private ArrayList<Room> rooms;
	
	@Persistent(mappedBy="hotel", dependentElement="true")
	@Join
	private ArrayList<Reservation> reservations;
	
	public Hotel(String hotelId, String name, String location, String[] services, LocalDate seasonStart,
			LocalDate seasonEnding) {
		
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
		this.services = services;
		this.seasonStart = seasonStart;
		this.seasonEnding = seasonEnding;
//		this.rooms = new ArrayList<>();
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

	public LocalDate getSeasonStart() {
		return seasonStart;
	}

	public void setSeasonStart(LocalDate seasonStart) {
		this.seasonStart = seasonStart;
	}

//	public ArrayList<Room> getRooms() {
//		return rooms;
//	}
//
//	public void setRooms(ArrayList<Room> rooms) {
//		this.rooms = rooms;
//	}
	
	public LocalDate getSeasonEnding() {
		return seasonEnding;
	}

	public void setSeasonEnding(LocalDate seasonEnding) {
		this.seasonEnding = seasonEnding;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}	
}
