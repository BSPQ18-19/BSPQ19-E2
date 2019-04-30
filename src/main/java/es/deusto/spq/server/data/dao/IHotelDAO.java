package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import es.deusto.spq.server.data.jdo.Hotel;

/** DAO interface for Hotels
 * @author gonzalo
 *
 */
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
	
	/** Retrieve all the hotels available since the requested arrival date
	 * @param arrivalDate Requested arrival date to book a hotel
	 * @return ArrayList of Hotel objects
	 */
	public ArrayList<Hotel> getHotels(Timestamp arrivalDate);

	/**
	 * Deletes a hotel by ID
	 * @param hotelID
	 * @return true if its been deleted, false if not
	 */
	public boolean deleteHotel(String hotelID);

	/**
	 * Clean all the DB
	 */
	public void cleanHotelsDB();
}
