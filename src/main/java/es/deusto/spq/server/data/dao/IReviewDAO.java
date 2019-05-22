package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.Review;

/**
 * The interface for the ReviewDAO.
 *
 * @author egoes
 *
 */
public interface IReviewDAO {

	/**
	 * Stores a review in the database.
	 *
	 * @param r       the review we want to store.
	 * @return a detached copy of the new review or null if there's an error.
	 */
	Review storeReview(Review r);

	/**
	 * Deletes a review from the DB.
	 *
	 * @param reviewID the id of the review we want to delete from the DB.
	 * @return true if the review has been deleted, false if there's no such review
	 *         stored.
	 */
	boolean deleteReview(String reviewID);

	/**
	 * Checks if user is able to store a review because each user can only store one
	 * review for each hotel.
	 *
	 * @param reviewID The id of the review to see if there already exist.
	 * @return return true if the user is able to post a review.
	 */
	boolean checkUserReview(String reviewID);

	/**
	 * Retrieves the reviews of a hotel.
	 *
	 * @param hotelID the id of the hotel you want the reviews from.
	 * @return the list of the reviews.
	 */
	List<Review> getReviewsOfHotel(String hotelID);

	/**
	 * Retrieves the reviews posted by a user.
	 *
	 * @param userID the id of the user you want the reviews from.
	 * @return the list of the reviews.
	 */
	List<Review> getReviewsByUser(String userID);
}
