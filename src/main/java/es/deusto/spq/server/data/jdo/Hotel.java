package es.deusto.spq.server.data.jdo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Hotel {
	
	@PrimaryKey
	private String hotelId;
	private String name;
	private String location;
	private Timestamp seasonStart;
	private Timestamp seasonEnding;
	
	@Persistent(defaultFetchGroup="true", mappedBy="hotel", dependentElement = "true")
	private List<Review> reviews;
	
	public Hotel(String hotelId, String name, String location, Timestamp seasonStart,
			Timestamp seasonEnding) {
		
		this.hotelId = hotelId;
		this.name = name;
		this.location = location;
		this.seasonStart = seasonStart;
		this.seasonEnding = seasonEnding;
		reviews = new ArrayList<Review>();
	}

	public void addReview(Review r) {
		reviews.add(r);
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
}
