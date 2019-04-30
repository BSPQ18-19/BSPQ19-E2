package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/** DTO class of Hotel
 * @author gonzalo
 *
 */
public class HotelDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String hotelId;
	private String name;
	private String location;
	private Timestamp seasonStart;
	private Timestamp seasonEnding;
	
	/** Constructor of HotelDTO
	 * @param hotelId Id of hotel
	 * @param name name of hotel
	 * @param location hotel's location
	 * @param seasonStart date when the hotel starts being available
	 * @param seasonEnding date when the hotel ends being available
	 */
	public HotelDTO(String hotelId, String name, String location, Timestamp seasonStart,
			Timestamp seasonEnding) {
		
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
		this.seasonStart = seasonStart;
		this.seasonEnding = seasonEnding;
	}

	/**
	 * @return ID of hotelDTO
	 */
	public String getHotelId() {
		return hotelId;
	}

	/** Set the hotelDTO id value
	 * @param hotelId Id of hotelDTO
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return name of the hotelDTO
	 */
	public String getName() {
		return name;
	}

	/** Set the name of hotelDTO
	 * @param name name of hotelDTO
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return location of hotelDTO
	 */
	public String getLocation() {
		return location;
	}

	/** Set the location hotelDTO
	 * @param location location of hotelDTO
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return SeasonStart date of hotelDTO
	 */
	public Timestamp getSeasonStart() {
		return seasonStart;
	}

	/** Set the SeasonStart date of hotelDTO
	 * @param seasonStart SeasonStart date of hotelDTO
	 */
	public void setSeasonStart(Timestamp seasonStart) {
		this.seasonStart = seasonStart;
	}
	
	/**
	 * @return SeasonEnding date of hotelDTO
	 */
	public Timestamp getSeasonEnding() {
		return seasonEnding;
	}

	/** Set the SeasonEnding date of hotelDTO
	 * @param seasonEnding SeasonEnding date of hotelDTO
	 */
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
