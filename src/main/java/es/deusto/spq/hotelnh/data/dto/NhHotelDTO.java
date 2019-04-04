package es.deusto.spq.hotelnh.data.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class NhHotelDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hotelId;
	private String name, location;
	private String[] services;
	private LocalDate seasonStart, seasonEnding;
	
	public NhHotelDTO(String hotelId, String name, String location, String[] services, LocalDate seasonStart,
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

	public LocalDate getSeasonEnding() {
		return seasonEnding;
	}

	public void setSeasonEnding(LocalDate seasonEnding) {
		this.seasonEnding = seasonEnding;
	}	
}