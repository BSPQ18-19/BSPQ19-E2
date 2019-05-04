package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Reservation;

public interface IReservationDAO {

	public Reservation getReservationbyID(String ID);
	public List<Reservation> getReservationsOfGuest(Guest guest);
	public Reservation createReservation(Reservation reservation);
	public boolean deleteReservationByID(String ID);
	
}
