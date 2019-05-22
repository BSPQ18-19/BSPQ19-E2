package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Reservation;

public interface IReservationDAO {

	/**Retrieves the reservation in the database that matches the given ID.
	 * @param ID - the ID of the reservation-
	 * @return the reservation.
	 */
	public Reservation getReservationbyID(String ID);
	
	/**
	 * Retrieves all the reservations the guest has made.
	 */
	public List<Reservation> getReservationsOfGuest(String guestID);

	/**Creates the given reservation and stores it in the database.
	 * @param reservation - the reservation to be created.
	 * @return a detached copy of the reservation.
	 */
	public Reservation createReservation(Reservation reservation);
	
	/**Removes the reservation with the given ID from the database.
	 * @param ID - the ID of the reservation to be returned.
	 * @return {@code true} if the deletion has been performed successfully, and 
	 * 			{@code false} if not.
	 */
	public boolean deleteReservationByID(String ID);
	
}
