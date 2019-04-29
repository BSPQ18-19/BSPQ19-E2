package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import es.deusto.spq.server.data.jdo.Review;

/**
 * The DTO of the review object.
 *
 * @author egoes
 *
 */
public class ReviewDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The id of the review.
	 */
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
	 * The hotel DTO where the review is posted.
	 */
	private HotelDTO hotel;
	/**
	 * The user DTO that posts the review.
	 */
	private UserDTO user;

	/**
	 *
	 * @param reviewID    the id to identify the review if needed.
	 * @param opinion     the opinion of the user about a hotel.
	 * @param score       the score(0-10) that a user gives a hotel..
	 * @param publishDate the publishDate of the review.
	 * @param hotel       the hotel DTO from the hotel that is the review abou.t
	 * @param user        the user DTO of the user that has posted the review.
	 */
	public ReviewDTO(String reviewID, String opinion, int score, Timestamp publishDate, HotelDTO hotel, UserDTO user) {
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
	 * @return The hotelDTO which the review is from.
	 */
	public HotelDTO getHotel() {
		return hotel;
	}
	/**
	 *
	 * @param hotel The hotelDTO new hotel you want to set.
	 */
	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	/**
	 *
	 * @return The user that has wrote the review.
	 */
	public UserDTO getUser() {
		return user;
	}
	/**
	 *
	 * @param user The new user you want to set.
	 */
	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", opinion=" + opinion + ", score=" + score + ", publishDate="
				+ publishDate + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReviewDTO reviewDTO = (ReviewDTO) o;
		return getReviewID().equals(reviewDTO.getReviewID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReviewID());
	}
}
