package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.deusto.spq.server.data.jdo.Hotel;
import junit.framework.Assert;
/**
 * The test-class for {@link es.deusto.spq.server.data.dao.HotelDAO}.
 *
 * <p>
 * Methods have a character at the beginning in order to sort them using both
 * {@link org.junit.FixMethodOrder} and {@link org.junit.runners.MethodSorters}.
 * </p>
 *
 * @author egoes
 *
 */
@SuppressWarnings("deprecation")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HotelDAOTest {

	//The DAO class
	private static HotelDAO hotelDAO;
	
	//The hotel used for test
	private static String hotelID;
	private static Hotel hotel;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hotelDAO = new HotelDAO();

		hotelID = "1";
		LocalDate localDate = LocalDate.of(2019, 04, 01);
		hotel = new Hotel(hotelID, "TEST", "TEST", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay()));
	}

	/**
	 * Test method to store a hotel on the DB.
	 * Checks if the copy that it returns is the same from the one we have.
	 */
	@Test
	public void aStoreHotel() {
		Hotel detachedHotel = hotelDAO.storeHotel(hotel);
		Assert.assertTrue(hotel.equals(detachedHotel));
	}	

	/**
	 * Test the method that gets a hotel by ID.
	 * It checks if the hotel we get is the same from the one we have.
	 */
	@Test
	public void bGetHotel() {
		Hotel detachedHotel = hotelDAO.getHotel(hotelID);
		Assert.assertTrue(hotel.equals(detachedHotel));
	}

	/**
	 * Test the method that gets all the hotels.
	 * Checks if the one we have stored is on the list
	 */
	@Test
	public void bGetHotels() {
		ArrayList<Hotel> hotels = hotelDAO.getHotels();
		Assert.assertTrue(hotels.contains(hotel));
	}

	/**
	 * Test the method for deleting a hotel from the ID.
	 * Checks if return is true, if it is hotel was deleted.
	 */
	@Test
	public void cDeleteHotel() {
		Assert.assertTrue(hotelDAO.deleteHotel(hotelID));
	}
}
