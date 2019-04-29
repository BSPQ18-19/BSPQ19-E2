package es.deusto.spq.server.data.jdo;

import java.sql.Timestamp;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Hotel {

	@PrimaryKey
	private String hotelId;
	private String name;
	private String location;
	private Timestamp seasonStart;
	private Timestamp seasonEnding;
	
	public Hotel(String hotelId, String name, String location, Timestamp seasonStart,
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

	/**
	 * Returns true if the ID is the same
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Hotel) {
			final Hotel object = (Hotel) obj;
			return object.getHotelId().equals(hotelId);
		}
		return false;
	}
}
