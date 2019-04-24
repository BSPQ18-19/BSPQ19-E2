package es.deusto.spq.client.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public class HotelManagementController {

	private static HotelManagementController controller = new HotelManagementController();
	private RMIServiceLocator rsl;
	private UserDTO loggedUser = null;
	private Logger log;
	private ArrayList<HotelDTO> currentHotels;
	
	private HotelManagementController() {
		rsl = RMIServiceLocator.getServiceLocator();
		log = ClientLogger.getLogger();
		log.info("HotelManagementController initialized");
		this.currentHotels = new ArrayList<>();
	}
	
	public static HotelManagementController getController() {
		return controller;
	}
	
	public UserDTO signInGuest(String name, String email, String password, String phone, String address) throws RemoteException {
		log.info("signInGuest called");
		UserDTO result = rsl.getHotelManager().signInGuest(name, email, password, phone, address);
		if(result != null)
			log.info("Signed in user with email: " + email);
		else
			log.info("Did not signed in user with email: " + email);
		return result;
	}
	
	public UserDTO logIn(String email, String password) throws RemoteException {
		if(loggedUser != null)
			logOut();
		loggedUser = rsl.getHotelManager().logIn(email, password);
		if(loggedUser != null)
			log.info("Logged in user with email: " + email);
		else
			log.info("Did not logged in user with email: " + email);
		return loggedUser;
	}
	
	public boolean logOut() throws RemoteException {
		if(loggedUser == null) {
			log.info("Did not logged out any user - no users were logged");
			return false;
		}
		rsl.getHotelManager().logOut(loggedUser);
		log.info("Logged out user with ID: " + loggedUser.getUserID());
		loggedUser = null;
		return true;
	}
	
	/** Create a new hotel
	 * @param id Id of the hotel
	 * @param name Name of the hotel
	 * @param location Location of the hotel
	 * @param seasonStart Day where the hotel starts being available
	 * @param seasonEnd Day where the hotel ends being available
	 * @return true if its properly created
	 */
	public boolean createHotel(String id, String name, String location, String seasonStart, String seasonEnd) {
    	
    	try {
    		log.info("Creating new hotel...");
			HotelDTO hotelDTO = rsl.getHotelManager().createHotel(id, name, location, seasonStart, seasonEnd);
			if(hotelDTO!=null) {
				log.info("Hotel created successfully!");
				return true;
			}else {
				log.info("Hotel cannot be created.");
			}
		} catch (RemoteException e) {
			log.fatal("Error creating a new hotel: " + e.getMessage());
		}
    	return false;
    }
    
    /** Delete a hotel using the hotelID
     * @param id Id of the hotel
     * @return true if its properly deleted
     */
    public boolean deleteHotel(String id) {
    	try {
    		log.info("Deleting hotel with ID: " + id);
			if(rsl.getHotelManager().deleteHotel(id)) {
				log.info("Hotel deleted successfully!");
				return true;
			}else {
				log.info("Hotel cannot be deleted");
			}
		} catch (RemoteException e) {
			log.fatal("Error deleting an hotel...");
		}
    	return false;
    }
    
    /** Retrieve all the hotels from DB
     * @return An array list of HotelDTO objects
     */
    public ArrayList<HotelDTO> retrieveHotels() {
    	log.info("Getting list of hotels.");
    	try {
			ArrayList<HotelDTO> hotel = rsl.getHotelManager().retrieveHotels();
//			for(HotelDTO hotelDTO: hotel) {
//				System.out.println(hotelDTO.getLocation());
//			}
//			HotelDTO[] hotels = hotel.toArray(new HotelDTO[hotel.size()]);
			
			
			if(hotel != null && hotel.size() != 0) {
				log.info("List of hotels retrieved succesfully.");
				return hotel;
			}else {
				log.info("Could not retrieve list of hotels");
			}
    	} catch (RemoteException e) {
    		log.fatal("Error getting list of hotels: " + e.getMessage());
		}
		return null;
    }
    
    /** Retrieve available hotels with the requested arrival date
     * @param arrivalDate Date when a guest wants to arrive at the hotel
     * @return An array list of HotelDTO objects
     */
    public ArrayList<HotelDTO> retrieveHotels(String arrivalDate) {
    	log.info("Getting list of hotels.");
    	try {
			ArrayList<HotelDTO> hotel = rsl.getHotelManager().retrieveHotels(arrivalDate);			
			
			if(hotel != null && hotel.size() != 0) {
				log.info("List of hotels retrieved succesfully.");
				return hotel;
			}else {
				log.info("Could not retrieve list of hotels");
			}
    	} catch (RemoteException e) {
    		log.fatal("Error getting list of hotels: " + e.getMessage());
		}
		return null;
    }    
    
    /** Clean all the hotels from the DB
     * @return true if its properly cleaned
     */
    public boolean cleanDB() {
    	try {
    		rsl.getHotelManager().cleanDB();
			return true;
		} catch (RemoteException e) {
			log.fatal("Error cleaning db: " + e.getMessage());
		}
    	return false;
    }
    
	/**
	 * @return An array list of HotelDTO objects
	 */
	public ArrayList<HotelDTO> getCurrentHotels() {
		return currentHotels;
	}
	
	/** Set the current hotels available
	 * @param hotelDTO HotelDTO object
	 */
	public void setCurrentHotels(HotelDTO hotelDTO) {
		this.currentHotels.add(hotelDTO);
	}
	/**
	 * Clear the list of the current hotels
	 */
	public void setCurrentHotels() {
		this.currentHotels.clear();
	}
	
	public UserDTO getLoggedUser() {
		return loggedUser;
	}

}
