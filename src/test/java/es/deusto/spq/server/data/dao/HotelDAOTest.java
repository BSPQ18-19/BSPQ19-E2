package es.deusto.spq.server.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.deusto.spq.server.data.jdo.Hotel;

@RunWith(MockitoJUnitRunner.class)
public class HotelDAOTest {
	
	Hotel hotel1;
	
	@Mock
	HotelDAO dao;
	
	@Before
	public void setUp() {
		hotel1 = new Hotel("H", "Hotel", "Bilbao", Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2020, 04, 28).atStartOfDay()));
		dao = new HotelDAO();
		dao.cleanDB();
		dao.storeHotel(hotel1);
	}
	
	@Test
	public void testRetieveHotels() {
		assertNotEquals(0, dao.getHotels().size());
	}

	//TODO 
//	@Test
//	public void testRetrieveHotelsById() {
//		System.out.println(hotel1);
//		System.out.println(dao.getHotel("H"));
//		assertEquals(hotel1, dao.getHotel("H"));
//		
//	}
	
//	@Test
//	public void testGetHotelsByArrivalDate() {
//		System.out.println(dao.getHotels(Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay())).get(0));
//		
//		assertEquals(hotel1, dao.getHotels(Timestamp.valueOf(LocalDate.of(2020, 01, 01).atStartOfDay())).get(0));
//
//	}
}
