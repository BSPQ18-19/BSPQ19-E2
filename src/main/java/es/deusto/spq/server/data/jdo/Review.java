package es.deusto.spq.server.data.jdo;

import java.sql.Timestamp;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jws.Oneway;

@PersistenceCapable(detachable = "true")
public class Review {
	
	@PrimaryKey
	private String reviewID;
	private String opinion;
	private int score;
	private Timestamp publishDate;	
	private Hotel hotel;
	
	public Review(String reviewID, String opinion, int score, Timestamp publishDate) {
		super();
		this.reviewID = reviewID;
		this.opinion = opinion;
		this.score = score;
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
	
	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", opinion=" + opinion + ", score=" + score + ", publishDate="
				+ publishDate + ", hotel=" + hotel.getHotelId() + "]";
	}	
}
