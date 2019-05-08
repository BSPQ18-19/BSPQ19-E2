package es.deusto.spq.server.data.dto;

import java.util.ArrayList;
import java.util.List;


public class ReservationDTO {

	/** The ID of the reservation. */
	private String id;
	/** The guest that has made the reservation. */
	private String guestId;
	/** The list of rooms related to the reservation. */
	private List<RoomDTO> rooms;
	
	/**Creates a new instance of the ReservationDTO with the given data.
	 * @param id - the ID of the reservation.
	 * @param guestId - the ID of the guest that has made the reservation.
	 */
	public ReservationDTO(String id, String guestId) {
		this.id = id;
		this.guestId = guestId;
		this.rooms = new ArrayList<RoomDTO>();
	}

	/**Creates a new instance of the ReservationDTO with the given data.
	 * @param id - the ID of the reservation.
	 * @param guestId - the ID of the guest that has made the reservation.
	 * @param rooms - The list of rooms (DTO) related to this reservation.
	 */
	public ReservationDTO(String id, String guestId, List<RoomDTO> rooms) {
		super();
		this.id = id;
		this.guestId = guestId;
		this.rooms = rooms;
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

	/**Retrieves the list of rooms related to this reservation.
	 * @return the list of rooms.
	 */
	public List<RoomDTO> getRooms() {
		return rooms;
	}

	/**Sets the list of rooms related to this reservation.
	 * @param RoomDTOs - the list of rooms.
	 */
	public void setRooms(List<RoomDTO> RoomDTOs) {
		this.rooms = RoomDTOs;
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
