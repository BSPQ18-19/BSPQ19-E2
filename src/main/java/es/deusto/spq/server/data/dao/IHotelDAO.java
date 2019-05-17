package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.util.List;

import es.deusto.spq.server.data.jdo.Hotel;
/** DAO interface for Hotels
 * @author gonzalo
 *
 */
public interface IHotelDAO {
	/**
	 * Stores a hotel in to the DB
	 * @param hotel the hotel you want to store
	 * @return Hotel object
	 */
	public Hotel storeHotel(Hotel hotel);

	/**
	 * Gets Hotel by ID from the DB
	 * @param hotelID the ID from the hotel we want to search
	 * @return Hotel object
	 */
	public Hotel getHotel(String hotelID);

	/**
	 * Gets all the hotels from the DB
	 * @return list of hotels
	 */
	public List<Hotel> getHotels();
	
	/** Retrieve all the hotels available since the requested arrival date
	 * @param arrivalDate Requested arrival date to book a hotel
	 * @return ArrayList of Hotel objects
	 */
	public List<Hotel> getHotels(Timestamp arrivalDate);

	/**
	 * Deletes a hotel by ID
	 * @param hotelID id of the hotel
	 * @return true if its been deleted, false if not
	 */
	public boolean deleteHotel(String hotelID);

	/**
	 * Clean all the DB
	 */
	public void cleanHotelsDB();
	
	/** Updates a hotel
	 * @param hotel Hotel object
	 * @return Hotel object
	 */
	public Hotel updateHotel(Hotel hotel);
}
