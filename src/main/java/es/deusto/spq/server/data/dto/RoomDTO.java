package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import es.deusto.spq.server.data.jdo.RoomType;

/** DTO class of rooms
 * @author gonzalo
 *
 */
public class RoomDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String roomID;
	private float size;
	private float price;
	private RoomType type;
	private boolean isOccupied;
	
	/** Constructor of RoomDTO
	 * @param roomID ID of room
	 * @param size Size of room
	 * @param price Price of room
	 * @param type Type of room
	 * @param isOccupied True if the room is occupied
	 */
	public RoomDTO(String roomID, float size, float price, RoomType type, boolean isOccupied) {
		super();
		this.roomID = roomID;
		this.size = size;
		this.price = price;
		this.type = type;
		this.isOccupied = isOccupied;
	}

	/**
	 * @return true if roomDTO is occupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/** Set true if roomDTO is occupied
	 * @param isOccupied true if roomDTO is occupied
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	/**
	 * @return Id of roomDTO
	 */
	public String getRoomID() {
		return roomID;
	}

	/** Set Id of roomDTO
	 * @param roomID Id of roomDTO
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	/**
	 * @return Size of roomDTO
	 */
	public float getSize() {
		return size;
	}

	/** Set Size of roomDTO
	 * @param size Size of roomDTO
	 */
	public void setSize(float size) {
		this.size = size;
	}

	/**
	 * @return Price of roomDTO
	 */
	public float getPrice() {
		return price;
	}

	/** Set Price of roomDTO
	 * @param price Price of roomDTO
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return Type of roomDTO
	 */
	public RoomType getType() {
		return type;
	}

	/** Set Type of roomDTO
	 * @param type Type of roomDTO
	 */
	public void setType(RoomType type) {
		this.type = type;
	}

	/**
	 * Check if the RoomDTO corresponds to the same object
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoomDTO roomDTO = (RoomDTO) o;
		return getRoomID().equals(roomDTO.getRoomID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoomID());
	}
}
