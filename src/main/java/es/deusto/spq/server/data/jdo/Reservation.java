package es.deusto.spq.server.data.jdo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**The reservation data model to be used in the server.  
 * @author Iker
 *
 */
@PersistenceCapable(detachable="true")
public class Reservation {

	/** The ID of the reservation. */
	@Persistent(defaultFetchGroup="true")
	@PrimaryKey
	private String reservationID;
	/** The guest that has made the reservation. */
	@Persistent(defaultFetchGroup="true")
	private String guestId;
	/** The list of rooms related to the reservation. */
	@Persistent(defaultFetchGroup="true")
	private String roomId;
	/** The timestamp in which the reservation has been made. */
	@Persistent(defaultFetchGroup="true")
	private Timestamp date;
	
	/**Creates a new instance of the Reservation with the given data. The date is also being stored.
	 * @param reservationID - the ID of the reservation.
	 * @param guestId - the guest that has made the reservation.
	 * @param roomId - the rooms related to this reservation.
	 */
	public Reservation(String reservationID, String guestId, String roomId) {
		super();
		this.reservationID = reservationID;
		this.guestId = guestId;
		this.roomId = roomId;
		date = Timestamp.valueOf(LocalDateTime.now());
	}
	
	/**Retrieves the reservation ID.
	 * @return the ID (String).
	 */
	public String getReservationID() {
		return reservationID;
	}
	
	/**Returns the guest's ID that has made the reservation.
	 * @return the guest's ID.
	 */
	public String getGuestId() {
		return guestId;
	}
	/**Sets the reservation ID to this DTO object.
	 * @param id - the new ID.
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

	/**Returnst the timestamp in which this reservation has been processed.
	 * @return the timestamp.
	 */
	public Timestamp getDate() {
		return date;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Reservation) {
			Reservation object = (Reservation) o;
			return object.getReservationID().equals(reservationID);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Reservation [reservationID=" + reservationID + ", guest=" + guestId + ", room=" + roomId + ", date="
				+ date + "]";
	}

}
