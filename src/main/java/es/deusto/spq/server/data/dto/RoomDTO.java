package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import es.deusto.spq.server.data.jdo.RoomType;

public class RoomDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String roomID;
	private float size;
	private float price;
	private RoomType type;
	private boolean isOccupied;
	private List<String> features;

	public RoomDTO(String roomID, float size, float price, RoomType type, boolean isOccupied, List<String> features) {
		super();
		this.roomID = roomID;
		this.size = size;
		this.price = price;
		this.type = type;
		this.isOccupied = isOccupied;
		this.features = features;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
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

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
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
