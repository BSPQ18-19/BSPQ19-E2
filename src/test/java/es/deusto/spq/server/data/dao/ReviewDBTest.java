package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.util.List;

import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

/**
 * The test-class for {@link es.deusto.spq.server.data.dao.ReviewDAO}.
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
public class ReviewDBTest {

	@Rule
	public ContiPerfRule rule = new ContiPerfRule();

	// The review used for testing
	private static String reviewID;
	private static Review r;

	// The hotel used for testing
	private static String hotelID;
	private static Hotel h;

	// The userID used for testing
	private static String userID;
	private static User u;

	// The DAO classes used for testing
	private static HotelDAO hotelDAO;
	private static ReviewDAO reviewDAO;
	private static UserDAO userDAO;

	@BeforeClass
	public static void setUp() throws Exception {
		//
		hotelDAO = new HotelDAO();
		reviewDAO = new ReviewDAO();
		userDAO = new UserDAO();

		hotelID = "TESTH";
		h = new Hotel(hotelID, "TEST", "TEST", Timestamp.valueOf("2019-03-02 00:00:00"),
				Timestamp.valueOf("2019-03-02 00:00:00"));
		hotelDAO.storeHotel(h);

		userID = "TESTU";
		u = new Guest(userID, "TEST", "TEST", "TEST", "TEST", "TEST");
		userDAO.createUser(u);

		reviewID = "TESTR";
		r = new Review(reviewID, "Test", 6, Timestamp.valueOf("2019-03-02 00:00:00"));
	}

	/**
	 * Stores a review and the DAO return the stored review to see if the review was
	 * correctly stored If the review is the same the test success
	 */
	@Test
	public void aStoreReview() {
		r.setHotel(h);
		r.setUser(u);
		Review detachedReview = reviewDAO.storeReview(r, hotelID, userID);
		Assert.assertTrue(r.equals(detachedReview));
	}

	/**
	 * Retrieves the reviews from a certain hotel and compares the first review with
	 * the one we created If the list contains the review we stored the test is a
	 * success
	 */
	@Test
	public void bRetriveReviewsOfHotel() {
		List<Review> reviews = reviewDAO.getReviewsOfHotel(hotelID);
		Assert.assertEquals(reviews.size(), 1);
		Assert.assertTrue(reviews.contains(r));
	}

	/**
	 * Retrieves the reviews posted by a user from the DB If the list contains the
	 * review we stored the test is a success
	 */
	@Test
	public void cRetrieveReviewsPostedByUser() {
		List<Review> reviews = reviewDAO.getReviewsByUser(userID);
		Assert.assertEquals(reviews.size(), 1);
		Assert.assertTrue(reviews.contains(r));
	}

	/**
	 * Deletes the review from the DB If the method return true the test is a
	 * success Here the hotel and the user are also deleted
	 */
	@Test
	public void dDeletesReview() {
		Assert.assertTrue(reviewDAO.deleteReview(reviewID));
		// TODO When the authorization on userDAO is done this null should chanage
		userDAO.deleteUserbyID(null, userID);
		hotelDAO.deleteHotel(hotelID);
	}
}
