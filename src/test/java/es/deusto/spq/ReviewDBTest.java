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
	 * Stores a review and the DAO return the stored review to see if the review was correctly stored
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void aStoreReview() {
		//Stores the review and return the stored review
		Review detachedCopy = hotelDAO.storeReview(r, hotelID);
		/*
		 * Compares the parameters to see if is the same review. 
		 * If the id is correct should be the same.
		 * If we wished to compare the objects itself insted of the parameters we would have to add the review to the hotel outside the DB.
		 */
		Assert.assertEquals(detachedCopy.getReviewID(), r.getReviewID());
		Assert.assertEquals(detachedCopy.getOpinion(), r.getOpinion());
		Assert.assertEquals(detachedCopy.getScore(), r.getScore());
		Assert.assertEquals(detachedCopy.getPublishDate(), r.getPublishDate());
	}
	
	/**
	 * Retrives a Hotel from DB and from that hotel it gets the review we have previously stored.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void bRetriveReviewFromHotel() {
		//Gets the hotel from the DB
		Hotel hotel = hotelDAO.getHotel(hotelID);
		
		//Gets the reviews from the hotel we retrived. here should be the one we previously stored.
		reviews = hotel.getReviews();
		Assert.assertEquals(1, reviews.size());
		
		Review re = reviews.get(0);
		/*
		 * Compares the parameters to see if is the same review. 
		 * If the id is correct should be the same.
		 * If we wished to compare the objects itself insted of the parameters we would have to add the review to the hotel outside the DB.
		 */
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
		//Deletes the review from DB and that also deletes the relation between this review and the hotel
		hotelDAO.deleteReview(r.getReviewID());
		Hotel hotel =  hotelDAO.getHotel(hotelID);
		reviews = hotel.getReviews();
		Assert.assertEquals(0, reviews.size());
		
		//Deletes the hotel in case the review was not previously deleted it would be deleted when deleting the hotel
		hotelDAO.deleteHotel(h.getHotelId());
	}
}
