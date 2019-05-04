package es.deusto.spq.server.data.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Reservation;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ReservationDAOTest {

	private static Reservation reservation;
	private static String id;
	private static Guest guest;
	private static IReservationDAO dao;

	@BeforeClass
	public static void initialize() {
		id = "RIDTest";
		guest = new Guest("GIDTest", "name");
		reservation = new Reservation(id, guest);
		dao = new ReservationDAO();
		dao.createReservation(reservation);
	}
	
	@AfterClass
	public static void tearDown() {
		dao.deleteReservationByID(id);
		new UserDAO().deleteUserbyID(null, guest.getUserID());
	}
	
	@Test
	public void reservationsByIDTest() {
		Assert.assertEquals(reservation, dao.getReservationbyID(id));
	}
	
	@Test
	public void reservationsOfGuestTest() {
		List<Reservation> reservations = dao.getReservationsOfGuest(guest);
		if(reservations == null || reservations.isEmpty())
			fail("No reservations found");
		if(reservations.size() > 1)
			fail("Multiple reservations found");
		Assert.assertEquals(reservation, reservations.get(0));
	}

}
