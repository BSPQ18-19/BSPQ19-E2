package es.deusto.spq.server.data.dao;

import java.util.ArrayList;

import es.deusto.spq.server.data.jdo.Hotel;

public interface IHotelDAO {

	/**
	 * Stores a hotel in to the DB
	 * @param hotel the hotel you want to store
	 * @return
	 */
	public Hotel storeHotel(Hotel hotel);

	/**
	 * Gets Hotel by ID from the DB
	 * @param hotelID the ID from the hotel we want to search
	 * @return
	 */
	public Hotel getHotel(String hotelID);

	/**
	 * Gets all the hotels from the DB
	 * @return arraylist of hotels
	 */
	public ArrayList<Hotel> getHotels();

	/**
	 * Deletes a hotel by ID
	 * @param hotelID
	 * @return true if its been deleted, false if not
	 */
	public boolean deleteHotel(String hotelID);

	/**
	 * Clean all the DB
	 */
	public void cleanDB();
}
