package es.deusto.spq.server.data.jdo;

import java.util.Objects;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/** Room class
 * @author gonzalo
 *
 */
@PersistenceCapable
public class Room {
	
	@PrimaryKey
	private String roomId;
	private float size; 
	private float price; 
	private RoomType type;
	private boolean isOccupied;
	
	private Hotel hotel;
	
	@Persistent(mappedBy="room")
	private Reservation reservation;
	
	/** Constructor of Room class
	 * @param roomId Id of the room
	 * @param size Size in square meters
	 * @param price Price per night
	 * @param type Single, double, triple or suite
	 * @param isOccupied Occupied or vacant
	 */
	public Room(String roomId, float size, float price, RoomType type, boolean isOccupied) {
		super();
		this.roomId = roomId;
		this.size = size;
		this.price = price;
		this.type = type;
		this.isOccupied = isOccupied;
	}

	/**
	 * @return Id of room
	 */
	public String getRoomId() {
		return roomId;
	}
	
	/** Set Id of room
	 * @param roomId Id of room
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * @return Size of room
	 */
	public float getSize() {
		return size;
	}
	/** Set Size of room
	 * @param size Size of room
	 */
	public void setSize(float size) {
		this.size = size;
	}

	/**
	 * @return Price of room
	 */
	public float getPrice() {
		return price;
	}

	/** Set Price of room
	 * @param price Price of room
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return Type of room
	 */
	public RoomType getType() {
		return type;
	}

	/** Set Type of room
	 * @param type Type of room
	 */
	public void setType(RoomType type) {
		this.type = type;
	}

	/**
	 * @return true if room is occupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/** Set true if room is occupied
	 * @param isOccupied true if room is occupied
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	
	/**
	 * @return hotel associated with the room
	 */
	public Hotel getHotel() {
		return hotel;
	}

	/** Set the hotel associated with the room
	 * @param hotel Hotel associated with the room
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Room room = (Room) o;
		return getRoomId().equals(room.getRoomId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoomId());
	}
}