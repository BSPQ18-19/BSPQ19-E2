package es.deusto.spq.server.data.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Room {
	
	@PrimaryKey
	private String roomId;
	private float size; 
	private float price; 
	private RoomType type;
	private boolean isOccupied;
	

	
	/**
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
}