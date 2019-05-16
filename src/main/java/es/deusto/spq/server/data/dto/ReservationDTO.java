package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ReservationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The ID of the reservation. */
	private String id;
	/** The guest that has made the reservation. */
	private String guestId;
	/** The list of rooms related to the reservation. */
	private String roomId;
	
	/**Creates a new instance of the ReservationDTO with the given data.
	 * @param id - the ID of the reservation.
	 * @param guestId - the ID of the guest that has made the reservation.
	 */
	public ReservationDTO(String id, String guestId, String roomId) {
		this.id = id;
		this.guestId = guestId;
		this.roomId = roomId;
	}

	/**Retrieves the reservation ID.
	 * @return the ID (String).
	 */
	public String getId() {
		return id;
	}

	/**Sets the reservation ID to this DTO object.
	 * @param id - the new ID.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**Returns the guest's ID that has made the reservation.
	 * @return the guest's ID.
	 */
	public String getGuestId() {
		return guestId;
	}

	/**Sets the guest ID that has make the reservation.
	 * @param guestId - the new ID.
	 */
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}

	
	/**Returns the room's ID that has made the reservation.
	 * @return the room's ID.
	 */
	public String getRoomId() {
		return roomId;
	}

	/**Sets the room ID that has make the reservation.
	 * @param roomId - the new ID.
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof ReservationDTO) {
			ReservationDTO object = (ReservationDTO) o;
			return object.getId().equals(id);
		}
		return false;
	}
	
}
