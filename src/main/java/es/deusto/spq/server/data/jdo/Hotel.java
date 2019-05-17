package es.deusto.spq.server.data.jdo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/** Hotel class
 * @author gonzalo
 *
 */
@PersistenceCapable(detachable = "true")
public class Hotel implements Serializable {

	@PrimaryKey
	private String hotelId;
	private String name;
	private String location;
	private Timestamp seasonStart;
	private Timestamp seasonEnding;
	
	@Persistent(mappedBy="hotel")
	private List<Room> listRooms;
	
	/** Constructor of Hotel
	 * @param hotelId Id of hotel
	 * @param name name of hotel
	 * @param location hotel's location
	 * @param seasonStart date when the hotel starts being available
	 * @param seasonEnding date when the hotel ends being available
	 */
	public Hotel(String hotelId, String name, String location, Timestamp seasonStart,
			Timestamp seasonEnding) {
		
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
		this.seasonStart = seasonStart;
		this.seasonEnding = seasonEnding;
		this.listRooms = new ArrayList<Room>();
	}
	
	/**
	 * @return ID of hotel
	 */
	public String getHotelId() {
		return hotelId;
	}

	/** Set the hotel id value
	 * @param hotelId Id of hotel
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return name of the hotel
	 */
	public String getName() {
		return name;
	}

	/** Set the name of hotel
	 * @param name name of hotel
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return location of hotel
	 */
	public String getLocation() {
		return location;
	}

	/** Set the location hotel
	 * @param location location of hotel
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return SeasonStart date of hotel
	 */
	public Timestamp getSeasonStart() {
		return seasonStart;
	}

	/** Set the SeasonStart date of hotel
	 * @param seasonStart SeasonStart date of hotel
	 */
	public void setSeasonStart(Timestamp seasonStart) {
		this.seasonStart = seasonStart;
	}

	/**
	 * @return SeasonEnding date of hotel
	 */
	public Timestamp getSeasonEnding() {
		return seasonEnding;
	}

	/** Set the SeasonEnding date of hotel
	 * @param seasonEnding SeasonEnding date of hotel
	 */
	public void setSeasonEnding(Timestamp seasonEnding) {
		this.seasonEnding = seasonEnding;
	}

		
	/**
	 * @return list of rooms associated with the hotel
	 */
	public List<Room> getListRooms() {
		return listRooms;
	}

	/** Set a list of rooms associated with the hotel
	 * @param listRooms list of rooms associated with the hotel
	 */
	public void setListRooms(List<Room> listRooms) {
		this.listRooms = listRooms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hotel hotel = (Hotel) o;
		return getHotelId().equals(hotel.getHotelId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getHotelId());
	}
}
