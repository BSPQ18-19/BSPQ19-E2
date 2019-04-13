package es.deusto.spq.data.jdo;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import es.deusto.spq.server.data.jdo.Hotel;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class HotelTest {

	private static Hotel hotel = null;
	private Date date;
	
	@BeforeClass
	public static void constructor() {
		Date date = Date.valueOf(LocalDate.now());
		Hotel hotelTmp = new Hotel("test01", "nameTest", "locationTest", new Timestamp(date.getTime()), new Timestamp(date.getTime() * 2));
		hotel = Mockito.spy(hotelTmp);
	}
	
	/**
	 * Tests the getter and setter of the ID in Hotel
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void get_set_hotelID() {
		//Getter
		Mockito.doReturn("test01").when(hotel).getHotelId();
		Assert.assertEquals("test01", hotel.getHotelId());
		
		//Setter
		String updatedID = "test02";
		hotel.setHotelId(updatedID);
		Mockito.doReturn(updatedID).when(hotel).getHotelId();
		Assert.assertEquals(updatedID, hotel.getHotelId());
	}

}
