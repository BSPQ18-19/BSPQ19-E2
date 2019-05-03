package es.deusto.spq.server.data.dto;

import java.util.ArrayList;
import java.util.List;


public class ReservationDTO {

	private String id;
	private String guestId;
	private List<RoomDTO> rooms;
	
	public ReservationDTO(String id, String guestId) {
		this.id = id;
		this.guestId = guestId;
		this.rooms = new ArrayList<RoomDTO>();
	}

	public ReservationDTO(String id, String guestId, List<RoomDTO> rooms) {
		super();
		this.id = id;
		this.guestId = guestId;
		this.rooms = rooms;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	public List<RoomDTO> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomDTO> RoomDTOs) {
		this.rooms = RoomDTOs;
	}
	
}
