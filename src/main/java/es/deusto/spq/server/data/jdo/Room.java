package es.deusto.spq.server.data.jdo;

import java.util.List;

public class Room {
	private String roomId;
	private float size; 
	private float price; 
	private RoomType type;
	private boolean status;
	private List<String> features;
	
	
	/**
	 * @param roomId 	Id of the room
	 * @param size 		Size in square meters
	 * @param price		Price per night
	 * @param type		Single, double, triple or suite
	 * @param status	Occupied or vacant
	 * @param features	All the features that a room have
	 */
	public Room(String roomId, float size, float price, RoomType type, boolean status, List<String> features) {
		super();
		this.roomId = roomId;
		this.size = size;
		this.price = price;
		this.type = type;
		this.status = status;
		this.features = features;
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

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}
}