package es.deusto.spq.server.remote;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.deusto.spq.server.data.dao.DAO;
import es.deusto.spq.server.data.dao.IHotelDAO;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.User;

/**
 * A simple class to load some data into the database.
 * @author Iker
 *
 */
public class DataLoader {

	/** The hotels to be loaded into the database */
	private static List<Hotel> hotels = null;
	/** The IDs of the hotels to be loaded into the database */
	private static Set<String> hotelIDs = null;
	
	static {
		hotels = new ArrayList<Hotel>();
		hotelIDs = new HashSet<String>();
	}
	
	/**
	 * Cleans the database of Hotels and Users.
	 * @param dao A dao object to use for the database cleaning.
	 */
	public static void cleanDataBase(DAO dao) {
		dao.cleanDataBase(Hotel.class);
		dao.cleanDataBase(User.class);
	}
	
	/**
	 * Loads a set of hotels to the database.
	 * @param hotelDAO The DAO object to use for the hotel creation.
	 * @return A set containing all the IDs of the loaded hotels.
	 */
	public static Set<String> loadHotels(IHotelDAO hotelDAO) {
		initializeHotels();
		for(Hotel h : hotels)
			hotelDAO.createHotel(h);
		return hotelIDs;
	}
	
	/**
	 * Initializes the hotels and the hotelIDs.
	 */
	private static void initializeHotels() {
		LocalDate localDate = LocalDate.of(2019, 04, 01);

		hotels.add(new Hotel("H01", "Hotel1", "Bilbao", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotelIDs.add("H01");
		hotels.add(new Hotel("H02", "Hotel2", "Barcelona", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotelIDs.add("H02");
		hotels.add(new Hotel("H03", "Hotel3", "Madrid", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotelIDs.add("H03");
		hotels.add(new Hotel("H04", "Hotel4", "Sevilla", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotelIDs.add("H04");
		hotels.add(new Hotel("H05", "Hotel5", "Zaragoza", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotelIDs.add("H05");
		hotels.add(new Hotel("H06", "Hotel6", "Gijon", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotelIDs.add("H06");
	}

	/**
	 * Returns the list of hotels that have been loaded into the database.
	 * @return A list of hotels.
	 */
	public List<Hotel> getHotels(){
		return hotels;
	}
	
	/**
	 * Returns the list of hotels' IDs that have been loaded into the database.
	 * @return A set of IDs.
	 */
	public Set<String> getHotelIDs(){
		return hotelIDs;
	}
	
	/** The users to be loaded into the database. */
	private static List<User> users = null;
	/** The IDs of the users to be loaded into the database. */
	private static Set<String> userIDs = null;
	
	/**
	 * NOT FINISHED
	 * Loads a set of users to the database.
	 * @return A set containing all the IDs of the loaded hotels.
	 */
	public static Set<String> loadUsers() {
		//TODO
		return userIDs;
	}
	
	private static void initializeUsers() {
		//TODO
	}
	
	/**
	 * Retrieves the users that have been loaded into the database.
	 * @return A list of users.
	 */
	public List<User> getUsers(){
		return users;
	}
	
	/**
	 * Retrieves the list of users' IDs that have been loaded into the database.
	 * @return A set of IDs.
	 */
	public Set<String> getUserIDs(){
		return userIDs;
	}
	
}
