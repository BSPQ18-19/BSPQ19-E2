package es.deusto.spq.server.data.jdo;

import java.sql.Timestamp;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Review {
	
	@PrimaryKey
	private String reviewID;
	private String opinion;
	//The score goes from 0 to 10
	private int score;
	private Timestamp publishDate;
	//The hotel where the review is posted
	@ForeignKey
	private Hotel hotel;
	//The users that posts the review
	@ForeignKey
	private User user;
	
	public Review(String reviewID, String opinion, int score, Timestamp publishDate) {
		super();
		this.reviewID = reviewID;
		this.opinion = opinion;
		if(score < 0) {
			this.score = 0;
		}else if(score > 10){
			this.score = 10;
		}else {
			this.score = score;
		}
		this.publishDate = publishDate;
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
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", opinion=" + opinion + ", score=" + score + ", publishDate="
				+ publishDate + "]";
	}

	@Override
	public boolean equals(Object obj) {
		//Two Review are equal objects if and only if they have the same userID.
		if(obj instanceof Review) {
			Review object = (Review) obj;
			return object.getReviewID().equals(reviewID);
		}
		return false;
	}
	
}
