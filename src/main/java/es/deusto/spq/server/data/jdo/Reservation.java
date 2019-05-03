package es.deusto.spq.server.data.jdo;

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
public class Reservation {

	@Persistent(defaultFetchGroup="true")
	@PrimaryKey
	private String reservationID;
	@Persistent(defaultFetchGroup="true")
	@ForeignKey
	private Guest guest;
	@Persistent(defaultFetchGroup="true")
	@Join
	private List<Room> rooms;
	@Persistent(defaultFetchGroup="true")
	private Timestamp date;
	
	public Reservation(String reservationID, Guest guest, List<Room> rooms) {
		super();
		this.reservationID = reservationID;
		this.guest = guest;
		this.rooms = rooms;
		date = Timestamp.valueOf(LocalDateTime.now());
	}
	
	public Reservation(String reservationID, Guest guest) {
		super();
		this.reservationID = reservationID;
		this.guest = guest;
		this.rooms = new ArrayList<Room>();
		date = Timestamp.valueOf(LocalDateTime.now());
	}
	
	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public String getReservationID() {
		return reservationID;
	}

	public List<Room> getRooms() {
		return rooms;
	}

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
