package es.deusto.spq;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.mysql.cj.jdbc.interceptors.SessionAssociationInterceptor;

import org.junit.Test;

import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReviewDBTest {

	private static Review r;
	private static HotelDAO hotelDAO;
	private static Hotel h;
	private static String hotelID;
	private static List<Review> reviews;
	
	@BeforeClass
	public static void setUp() throws Exception {
		hotelID = "H69";
		reviews = new ArrayList<Review>();
		hotelDAO = new HotelDAO();
		h =new Hotel(hotelID, "Hotel1", "Bilbao", Timestamp.valueOf("2019-03-02 00:00:00"), Timestamp.valueOf("2019-03-02 00:00:00"));
		r = new Review("1", "Perfect", 9, Timestamp.valueOf("2019-03-02 00:00:00"));
		hotelDAO.storeHotel(h);
	}

	/**
	 * Stores a review and retrive it to see if the Review DAO and JDO work fine
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void aStoreReview() {
		Review re = hotelDAO.storeReview(r, hotelID);
		Assert.assertEquals(re.getReviewID(), r.getReviewID());
		Assert.assertEquals(re.getOpinion(), r.getOpinion());
		Assert.assertEquals(re.getScore(), r.getScore());
		Assert.assertEquals(re.getPublishDate(), r.getPublishDate());
	}
	
	/**
	 * Retrives a Hotel from DB and from that hotel it gets the review
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void bRetriveReviewFromHotel() {
		Hotel hotel = hotelDAO.getHotel(hotelID);
		
		reviews = hotel.getReviews();
		Assert.assertEquals(1, reviews.size());
		
		Review re = reviews.get(0);
		Assert.assertEquals(re.getReviewID(), r.getReviewID());
		Assert.assertEquals(re.getOpinion(), r.getOpinion());
		Assert.assertEquals(re.getScore(), r.getScore());
		Assert.assertEquals(re.getPublishDate(), r.getPublishDate());
	}
	
	/**
	 * Deletes the review and the hotel from the DB
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void cDeleteReview() {
		hotelDAO.deleteReview(r.getReviewID());
		Hotel hotel =  hotelDAO.getHotel(hotelID);
		reviews = hotel.getReviews();
		Assert.assertEquals(0, reviews.size());
		
		hotelDAO.deleteHotel(h.getHotelId());
	}
}
