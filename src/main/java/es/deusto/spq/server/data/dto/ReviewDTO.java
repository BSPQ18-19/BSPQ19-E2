package es.deusto.spq.server.data.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PrimaryKey;

import es.deusto.spq.server.data.jdo.Review;

/**
 * The DTO of the review object
 *
 * @author egoes
 *
 */
public class ReviewDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// The id of the review
	@PrimaryKey
	private String reviewID;
	// The opinion written by the user
	private String opinion;
	// The score goes from 0 to 10
	private int score;
	private Timestamp publishDate;
	// The hotel DTO where the review is posted
	@ForeignKey
	private HotelDTO hotel;
	// The user DTO that posts the review
	private UserDTO user;

	/**
	 *
	 * @param reviewID    the id to identify the review if needed
	 * @param opinion     the opinion of the user about a hotel
	 * @param score       the score that a user gives a hotel. From 0 to 10
	 * @param publishDate the publishDate of the review
	 * @param hotel       the hotel DTO from the hotel that is the review about
	 * @param user        the user DTO of the user that has posted the review
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

	public String getReviewID() {
		return reviewID;
	}

	public void setReviewID(String reviewID) {
		this.reviewID = reviewID;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Timestamp getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Timestamp publishDate) {
		this.publishDate = publishDate;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", opinion=" + opinion + ", score=" + score + ", publishDate="
				+ publishDate + "]";
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

}
