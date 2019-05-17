package es.deusto.spq.server.data.jdo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**The reservation data model to be used in the server.  
 * @author Iker
 *
 */
@PersistenceCapable(detachable="true")
public class Reservation implements Serializable {

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
	/** The timestamp of the first day in the hotel. */
	@Persistent(defaultFetchGroup="true")
	private Timestamp firstDay;
	/** The timestamp of the last day in the hotel. */
	@Persistent(defaultFetchGroup="true")
	private Timestamp lastDay;
	
	/**Creates a new instance of the Reservation with the given data. The date is also being stored.
	 * @param reservationID - the ID of the reservation.
	 * @param guestId - the guest that has made the reservation.
	 * @param roomId - the rooms related to this reservation.
	 * @param firstDay the timestamp of the first day in the hotel
	 * @param lastDay the timestamp of the last day in the hotel
	 */
	public Reservation(String reservationID, String guestId, String roomId, Timestamp firstDay, Timestamp lastDay ) {
		super();
		this.reservationID = reservationID;
		this.guestId = guestId;
		this.roomId = roomId;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
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

	/**Returnst the timestamp in which this reservation has been processed.
	 * @return the timestamp.
	 */
	public Timestamp getDate() {
		return date;
	}
	/** Returns the timestamp of the first day in the hotel
	 * @return firstDay the timestamp of the first day in the hotel
	 */
	public Timestamp getFirstDay() {
		return firstDay;
	}
	/** Sets the timestamp of the first day in the hotel
	 * @param firstDay the timestamp of the first day in the hotel
	 */
	public void setFirstDay(Timestamp firstDay) {
		this.firstDay = firstDay;
	}
	/** Returns the timestamp of the last day in the hotel
	 * @return lastDay the timestamp of the last day in the hotel 
	 */
	public Timestamp getLastDay() {
		return lastDay;
	}
	/** Sets the timestamp of the last day in the hotel
	 * @param lastDay the timestamp of the last day in the hotel
	 */
	public void setLastDay(Timestamp lastDay) {
		this.lastDay = lastDay;
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
	
	@Override
	public int hashCode() {
		return reservationID.hashCode();
	}

}
