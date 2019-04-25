package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.Review;

public interface IReviewDAO {
	/**
	 * Stores a review in the database
	 * @param r the review we want to store
	 * @param hotelID the id of the hotel the Review is about. Is used to check if review is possible
	 * @param userID the id of the user that post the review. Is used to check if review is possible
	 * @return a detached copy of the new review or null if there's an error
	 */
	public Review storeReview(Review r, String hotelID, String userID);
	/**
	 * Deletes a review from the DB
	 * @param reviewID the id of the review we want to delete from the DB
	 * @return true if
	 */
	public boolean deleteReview(String reviewID);
	/**
	 * Checks if user is able to store a review because each user can only store one review for each hotel
	 * @param hotelID The id of the hotel user want to store the review
	 * @param userID The id of the user that wants to store the review
	 * @return return true if the user is able to post a review
	 */
	public boolean checkUserReview(String hotelID, String userID);
	/**
	 * Retrieves the reviews of a hotel
	 * @param hotelID the id of the hotel you want the reviews from
	 * @return the list of the reviews
	 */
	public List<Review> getReviewsOfHotel(String hotelID);
	/**
	 * Retrieves the reviews posted by a user
	 * @param userID the id of the user you want the reviews from
	 * @return the list of the reviews
	 */
	public List<Review> getReviewsByUser(String userID);
}
