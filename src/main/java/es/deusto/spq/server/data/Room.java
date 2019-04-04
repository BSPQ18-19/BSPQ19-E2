package es.deusto.spq.server.data;

/**
 * @author garri
 *
 */
public class Room {
	private String roomId;
	private int size; 
	private int price; 
	private RoomType type;
	private RoomStatus status;
	private String[] features;
	
	
	/**
	 * @param roomId 	Id of the room
	 * @param size 		Size in square meters
	 * @param price		Price per night
	 * @param type		Single, double, triple or suite
	 * @param status	Occupied or vacant
	 * @param features	All the features that a room have
	 */
	public Room(String roomId, int size, int price, RoomType type, RoomStatus status, String[] features) {
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public String[] getFeatures() {
		return features;
	}

	public void setFeatures(String[] features) {
		this.features = features;
	}
}