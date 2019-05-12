package es.deusto.spq.server.data.jdo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.Join;
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
	@ForeignKey
	private Guest guest;
	/** The list of rooms related to the reservation. */
	@Persistent(defaultFetchGroup="true")
	@Join
	private List<Room> rooms;
	/** The timestamp in which the reservation has been made. */
	@Persistent(defaultFetchGroup="true")
	private Timestamp date;
	
	/**Creates a new instance of the Reservation with the given data. The date is also being stored.
	 * @param reservationID - the ID of the reservation.
	 * @param guest - the guest that has made the reservation.
	 * @param rooms - the rooms related to this reservation.
	 */
	public Reservation(String reservationID, Guest guest, List<Room> rooms) {
		super();
		this.reservationID = reservationID;
		this.guest = guest;
		this.rooms = rooms;
		date = Timestamp.valueOf(LocalDateTime.now());
	}
	
	/**Creates a new instance of the Reservation with the given data. The date is also being stored.
	 * @param reservationID - the ID of the reservation.
	 * @param guest - the guest that has made the reservation.
	 */
	public Reservation(String reservationID, Guest guest) {
		super();
		this.reservationID = reservationID;
		this.guest = guest;
		this.rooms = new ArrayList<Room>();
		date = Timestamp.valueOf(LocalDateTime.now());
	}
	
	/**Creates a new instance of the Reservation with the given data. The date is also being stored.
	 * @param reservationID - the ID of the reservation.
	 * @param rooms - the rooms related to this reservation.
	 */
	public Reservation(String reservationID, List<Room> rooms) {
		super();
		this.reservationID = reservationID;
		this.guest = null;
		this.rooms = rooms;
		date = Timestamp.valueOf(LocalDateTime.now());
	}
	
	/**Returns the guest that has made the reservation.
	 * @return the guest.
	 */
	public Guest getGuest() {
		return guest;
	}

	/**Sets the guest that has made the reservation.
	 * @param guest - the guest.
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**Retrieves the reservation ID.
	 * @return the ID (String).
	 */
	public String getReservationID() {
		return reservationID;
	}

	/**Returns the rooms related to this reservation.
	 * @return a list of rooms.
	 */
	public List<Room> getRooms() {
		return rooms;
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
		return "Reservation [reservationID=" + reservationID + ", guest=" + guest + ", numberRooms=" + rooms.size() + ", date="
				+ date + "]";
	}

}
