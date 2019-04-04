package es.deusto.spq;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.data.Hotel;
import es.deusto.spq.server.data.dao.HotelDAO;

public class HotelDAOTest {
	
	private static HotelDAO hotelDao;
	private static Hotel hotel;
	
	@Before
	public void init() {
		hotelDao = new HotelDAO();
		hotel = new Hotel("H11", "Hotel11", "Valladolid", null, LocalDate.of(2019, 02, 01), LocalDate.of(2019, 03, 31));
	}
	
	@Test
	public void createHotelTest() {
		hotelDao.storeHotel(hotel);
		List<Hotel> listHotel = hotelDao.getHotels();
		Assert.assertTrue(!listHotel.isEmpty());
	}

}
