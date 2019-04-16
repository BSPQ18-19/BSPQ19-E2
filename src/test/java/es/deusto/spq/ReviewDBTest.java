package es.deusto.spq;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

public class ReviewDBTest {

	private static Review r;
	private static HotelDAO hotelDAO;
	private static Hotel h;
	private static String hotelID;
	
	@BeforeClass
	public static void setUp() throws Exception {
		hotelID = "H69";
		hotelDAO = new HotelDAO();
		h =new Hotel(hotelID, "Hotel1", "Bilbao", Timestamp.valueOf("2019-03-02 00:00:00"), Timestamp.valueOf("2019-03-02 00:00:00"));
		r = new Review("1", "Perfect", 9, Timestamp.valueOf("2019-03-02 00:00:00"));
		hotelDAO.storeHotel(h);
		h.addReview(r);
	}

	/*
	 * Stores a review and retrive it to see if the Review DAO and JDO work fine
	 */
	@Test
	public void reviewDAOTest() {
		hotelDAO.storeReview(r, h.getHotelId());
		Hotel detachedHotel = hotelDAO.getHotel(hotelID);
		List<Review> reviews = h.getReviews();
		Assert.assertTrue(reviews.contains(r));
	}
	
	@After
	public void delete() {
		hotelDAO.deleteHotel(h.getHotelId());
	}
}
