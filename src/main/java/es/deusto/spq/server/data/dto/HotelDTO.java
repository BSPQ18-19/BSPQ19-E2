package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HotelDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hotelId;
	private String name, location;
	private String[] services;
	private Timestamp seasonStart, seasonEnding;
	
	public HotelDTO(String hotelId, String name, String location, String[] services, Timestamp seasonStart,
			Timestamp seasonEnding) {
		
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

	public Timestamp getSeasonStart() {
		return seasonStart;
	}

	public void setSeasonStart(Timestamp seasonStart) {
		this.seasonStart = seasonStart;
	}
	
	public Timestamp getSeasonEnding() {
		return seasonEnding;
	}

	public void setSeasonEnding(Timestamp seasonEnding) {
		this.seasonEnding = seasonEnding;
	}
}
