package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import es.deusto.spq.server.data.jdo.Hotel;

/** DAO interface for Hotels
 * @author gonzalo
 *
 */
public interface IHotelDAO {
	
	/** Stores the Hotel object into the database
	 * @param hotel Object of Hotel class
	 */
	public void storeHotel(Hotel hotel);
	
	/** Retrieves a hotel from the database according to the ID requested
	 * @param hotelID Id of the hotel
	 * @return Hotel object
	 */
	public Hotel getHotel(String hotelID);
	
	/** Retrieve all the hotels from the database
	 * @return ArrayList of Hotel objects
	 */
	public ArrayList<Hotel> getHotels();
	
	/** Retrieve all the hotels available since the requested arrival date
	 * @param arrivalDate Requested arrival date to book a hotel
	 * @return ArrayList of Hotel objects
	 */
	public ArrayList<Hotel> getHotels(Timestamp arrivalDate);
	
	/** Delete a hotel form the database with the requested ID
	 * @param HotelID Id of the hotel
	 * @return true if the database was clean successfully
	 */
	public boolean deleteHotel(String hotelID);
	
	/**
	 * Remove all the hotels form the database
	 */
	public void cleanDB();
}
