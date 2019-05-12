package es.deusto.spq.server.data.jdo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

/**
 * The class that defines the Review object.
 *
 * @author egoes
 *
 */
@PersistenceCapable(detachable = "true")
public class Review implements Serializable {

	/**
	 * The id of the review.
	 */
	@PrimaryKey
	private String reviewID;
	/**
	 * The opinion written by the user.
	 */
	private String opinion;
	/**
	 * The score goes from 0 to 10.
	 */
	private int score;
	/**
	 * The date the review was published.
	 */
	private Timestamp publishDate;
	/**
	 * The hotel where the review is posted.
	 */
	@ForeignKey
	private Hotel hotel;
	/**
	 * The user that posts the review.
	 */
	@ForeignKey
	private User user;

	/**
	 *
	 * @param reviewID    the id to identify the review if needed.
	 * @param opinion     the opinion of the user about a hotel.
	 * @param score       the score(0-10) that a user gives a hotel.
	 * @param publishDate the publishDate of the review.
	 */
	public Review(String reviewID, String opinion, int score, Timestamp publishDate) {
		super();
		this.reviewID = reviewID;
		this.opinion = opinion;
		if (score < 0)
			this.score = 0;
		else if (score > 10)
			this.score = 10;
		else
			this.score = score;
		this.publishDate = publishDate;
	}

	/**
	 *
	 * @param reviewID    the id to identify the review if needed.
	 * @param opinion     the opinion of the user about a hotel.
	 * @param score       the score(0-10) that a user gives a hotel.
	 * @param publishDate the publishDate of the review.
	 * @param hotel       the hotel from the hotel that is the review about.
	 * @param user        the user of the user that has posted the review.
	 */
	public Review(String reviewID, String opinion, int score, Timestamp publishDate, Hotel hotel, User user) {
		super();
		this.reviewID = reviewID;
		this.opinion = opinion;
		if (score < 0)
			this.score = 0;
		else if (score > 10)
			this.score = 10;
		else
			this.score = score;
		this.publishDate = publishDate;
		this.user = user;
		this.hotel = hotel;
	}
	/**
	 *
	 * @return The id of the review.
	 */
	public String getReviewID() {
		return reviewID;
	}
	/**
	 * Set a new ID.
	 * @param reviewID the new ID we want to set.
	 */
	public void setReviewID(String reviewID) {
		this.reviewID = reviewID;
	}
	/**
	 *
	 * @return The opinion the user wrote.
	 */
	public String getOpinion() {
		return opinion;
	}
	/**
	 *
	 * @param opinion The new opinion we want to set.
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	/**
	 *
	 * @return The score.
	 */
	public int getScore() {
		return score;
	}
	/**
	 *
	 * @param score The new score we want to set.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 *
	 * @return The date the review was published.
	 */
	public Timestamp getPublishDate() {
		return publishDate;
	}
	/**
	 *
	 * @param publishDate The new publishDate we want to set.
	 */
	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}
	/**
	 *
	 * @return The hotel which the review is from.
	 */
	public Hotel getHotel() {
		return hotel;
	}
	/**
	 *
	 * @param hotel The hotel new hotel you want to set.
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	/**
	 *
	 * @return The user that has wrote the review.
	 */
	public User getUser() {
		return user;
	}
	/**
	 *
	 * @param user The new user you want to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", opinion=" + opinion +
				", score=" + score + ", publishDate=" + publishDate + "]";
	}

	@Override
	public boolean equals(Object obj) {
		// Two Review are equal objects if and only if they have the same reviewID.
		if (obj instanceof Review) {
			final Review object = (Review) obj;
			return object.getReviewID().equals(reviewID);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return reviewID.hashCode();
	}

}
