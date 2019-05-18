package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.deusto.spq.server.data.jdo.Hotel;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HotelDAOTest {

	@Rule
	public ContiPerfRule rule = new ContiPerfRule();

	//The DAO class
	private static HotelDAO hotelDAO;
	
	//The hotel used for test
	private static String hotelID;
	private static Hotel hotel;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		hotelDAO = new HotelDAO();

		hotelID = "5";
		LocalDate localDate = LocalDate.of(2019, 04, 01);
		hotel = new Hotel(hotelID, "TEST", "TEST", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay()));
	}

	/**
	 * Test method to store a hotel on the DB.
	 * Checks if the copy that it returns is the same from the one we have.
	 */
	@Test
	public void test1StoreHotel() {
		Hotel detachedHotel = hotelDAO.storeHotel(hotel);
		Assert.assertTrue(hotel.equals(detachedHotel));
	}	

	/**
	 * Test the method that gets a hotel by ID.
	 * It checks if the hotel we get is the same from the one we have.
	 */
	@Test
	public void test2GetHotel() {
		Hotel detachedHotel = hotelDAO.getHotel(hotelID);
		Assert.assertTrue(hotel.equals(detachedHotel));
	}

	/**
	 * Test the method that gets all the hotels.
	 * Checks if the one we have stored is on the list.
	 */
	@Test
	public void test3GetHotels() {
		List<Hotel> hotels = hotelDAO.getHotels();
		Assert.assertTrue(hotels.contains(hotel));
	}

	/**
	 * Test the method for retrieving hotels according to an arrival date
	 */
	@Test
	public void test4GetHotelsByArrivalDate() {
		Hotel hotel = hotelDAO.getHotels(Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay())).get(0);
		Assert.assertEquals(Timestamp.valueOf(LocalDate.of(2019, 04, 01).atStartOfDay()), hotel.getSeasonStart());
	}
	
	/**
	 * Test the method for updating a hotel
	 */
	@Test
	public void test5UpdateHotel() {
		Hotel hotelToUpdate = new Hotel(hotelID, "SECONDTEST", "TEST", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
		Hotel hotelUpdated = hotelDAO.updateHotel(hotelToUpdate);
		Assert.assertEquals(hotelToUpdate.getName(), hotelUpdated.getName());
		Assert.assertEquals(hotelToUpdate.getSeasonStart(), hotelUpdated.getSeasonStart());
		Assert.assertEquals(hotelToUpdate.getSeasonEnding(), hotelUpdated.getSeasonEnding());
		
		Assert.assertNotEquals(hotel.getName(), hotelUpdated.getName());
		Assert.assertNotEquals(hotel.getSeasonStart(), hotelUpdated.getSeasonStart());
		Assert.assertNotEquals(hotel.getSeasonEnding(), hotelUpdated.getSeasonEnding());
	
	}
	
	/**
	 * Test the method for deleting a hotel from the ID.
	 * Checks if return is true, if it is hotel was deleted.
	 */
	@Test
	public void test6DeleteHotel() {
		Assert.assertTrue(hotelDAO.deleteHotel(hotelID));
		hotelDAO.cleanHotelsDB();
	}
}
