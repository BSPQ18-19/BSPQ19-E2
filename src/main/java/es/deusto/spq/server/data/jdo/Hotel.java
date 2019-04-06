package es.deusto.spq.server.data.jdo;

import java.time.LocalDate;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Hotel {
	
	@PrimaryKey
	private String hotelId;
	private String name;
	private String location;
	private List<String> services;
	private LocalDate seasonStart, seasonEnding;
	
	public Hotel(String hotelId, String name, String location, List<String> services, LocalDate seasonStart,
			LocalDate seasonEnding) {
		
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

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public LocalDate getSeasonStart() {
		return seasonStart;
	}

	public void setSeasonStart(LocalDate seasonStart) {
		this.seasonStart = seasonStart;
	}

	public LocalDate getSeasonEnding() {
		return seasonEnding;
	}

	public void setSeasonEnding(LocalDate seasonEnding) {
		this.seasonEnding = seasonEnding;
	}

}