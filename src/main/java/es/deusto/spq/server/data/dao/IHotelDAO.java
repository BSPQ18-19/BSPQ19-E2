package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.Hotel;

public interface IHotelDAO {
	
	/**
	 * Retrieves all the hotels in the database.
	 * @return A list of all the hotels.
	 */
	public List<Hotel> getHotels();
	/**
	 * Retrieves the user that matches the ID.
	 * @param hotelID The ID of the hotel to retrieve.
	 * @return The Hotel matching the ID if exits, and null if not.
	 */
	public Hotel getHotelbyID(String hotelID);
	/**
	 * Creates the hotel in the database, and returns a detached copy.
	 * @param hotel The new hotel to be stored into the database.
	 * @return A detached copy of the new hotel, and null if an error has arised.
	 */
	public Hotel createHotel(Hotel hotel);
	/**
	 * Deletes the hotel in the database, and returns a boolean indicating if it has been
	 * or not deleted.
	 * @param hotelID The ID of the hotel to be deleted from the database.
	 * @return True if the ID matched a hotel and has correctly been deleted. False if it
	 * 			didn't exist or couldn't manage to delete it.
	 */
	public boolean deleteHotelbyID(String hotelID);

}
