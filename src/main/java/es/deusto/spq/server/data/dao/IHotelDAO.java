package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;

public interface IHotelDAO {
	
	public void storeHotel(Hotel hotel);
	public Hotel getHotel(String hotelID);
	public ArrayList<Hotel> getHotels();
	public boolean deleteHotel(String hotelID);
	public void cleanDB();
	/**
	 * Stores a review in the database
	 * @param r the review we want to store
	 * 		  hotelID the hotel the Review is about
	 * 
	 */
	public Review storeReview(Review r, String hotelID);

}
