package es.deusto.spq.hotelnh.data;

import java.time.LocalDateTime;

public class NhHotel {
	
	private String hotelId;
	private String name, location;
	private String[] services;
	private LocalDateTime seasonStart, seasonEnding;
	//TODO Rooms
	
	public NhHotel(String hotelId, String name, String location, String[] services, LocalDateTime seasonStart,
			LocalDateTime seasonEnding) {
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

	public LocalDateTime getSeasonStart() {
		return seasonStart;
	}

	public void setSeasonStart(LocalDateTime seasonStart) {
		this.seasonStart = seasonStart;
	}

//	public ArrayList<Room> getRooms() {
//		return rooms;
//	}
//
//	public void setRooms(ArrayList<Room> rooms) {
//		this.rooms = rooms;
//	}
	
	public LocalDateTime getSeasonEnding() {
		return seasonEnding;
	}

	public void setSeasonEnding(LocalDateTime seasonEnding) {
		this.seasonEnding = seasonEnding;
	}	
}
