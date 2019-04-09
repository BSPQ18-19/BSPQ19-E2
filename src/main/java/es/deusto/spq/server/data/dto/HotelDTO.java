package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class HotelDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String hotelId;
	private String name;
	private String location;
	private Timestamp seasonStart;
	private Timestamp seasonEnding;
	
	public HotelDTO(String hotelId, String name, String location, Timestamp seasonStart,
			Timestamp seasonEnding) {
		
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
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
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof HotelDTO) {
			HotelDTO object = (HotelDTO) o;
			return hotelId.equals(object.getHotelId());
		}
		return false;
	}
}
