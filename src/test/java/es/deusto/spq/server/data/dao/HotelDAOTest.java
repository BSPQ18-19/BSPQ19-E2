package es.deusto.spq.server.data.dao;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.deusto.spq.server.data.jdo.Hotel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HotelDAOTest {
	
	private static Hotel hotel1;
	
	private static HotelDAO dao;
	
	@BeforeClass
	public static void setUp() throws Exception{
		hotel1 = new Hotel("Z", "Hotel", "Bilbao", Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2020, 04, 28).atStartOfDay()));
		
		dao = new HotelDAO();
		dao.storeHotel(hotel1);
	}
	
	@Test
	public void test1RetieveHotels() {
		assertNotEquals(0, dao.getHotels().size());
	}

	
	@Test
	public void test2GetHotelsByArrivalDate() {
		Hotel hotel = dao.getHotels(Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay())).get(0);
		assertEquals(Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay()), hotel.getSeasonStart());
	}
	
	@Test
	public void test3DeleteHotel() {
		assertTrue(dao.deleteHotel("Z"));
	}
}
