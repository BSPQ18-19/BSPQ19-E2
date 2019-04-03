package es.deusto.spq.hotelnh.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NhHotel {
	
	private String hotelId;
	private String name, location;
	private String[] services;
	private LocalDate seasonStart, seasonEnding;
	//TODO Rooms
	
	public NhHotel(String hotelId, String name, String location, String[] services, LocalDate seasonStart,
			LocalDate seasonEnding) {
		super();
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
		this.services = services;
		this.seasonStart = seasonStart;
		this.seasonEnding = seasonEnding;
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
}
