package es.deusto.spq.server.data.dao;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
		reservation = new Reservation(id, guest.getUserID(), "R01", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
		dao = new ReservationDAO();
		dao.createReservation(reservation);
	}
	
	@AfterClass
	public static void tearDown() {
		dao.deleteReservationByID(id);
		new UserDAO().deleteUserbyID(guest.getUserID());
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
