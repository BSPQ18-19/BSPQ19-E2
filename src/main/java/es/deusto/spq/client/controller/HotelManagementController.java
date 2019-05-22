package es.deusto.spq.client.controller;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.ReservationDTO;
import es.deusto.spq.server.data.dto.ReviewDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.RoomType;

/** Controller of the client side
 * @author gonzalo
 *
 */
public class HotelManagementController {

	private static HotelManagementController controller = new HotelManagementController();
	private RMIServiceLocator rsl;
	private UserDTO loggedUser = null;
	private Logger log;
	/**
	 * The total current hotels of the database
	 */
	private ArrayList<HotelDTO> currentHotels;
	/**
	 * The total current rooms of the database
	 */
	private ArrayList<RoomDTO> currentRooms;
	
	private HotelManagementController() {
		rsl = RMIServiceLocator.getServiceLocator();
		log = ClientLogger.getLogger();
		log.info("HotelManagementController initialized");
		this.currentHotels = new ArrayList<>();
		this.currentRooms = new ArrayList<>();
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
	
	public UserDTO signInAdmin(String name, String email, String password, String address) throws RemoteException {
		UserDTO result = rsl.getHotelManager().signInAdmin(name, email, password, address);
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
	
	/**
	 * The updateUser method that asks the server for the new DTO of the updated User.
	 * @param name
	 * @param email
	 * @param password
	 * @param phone
	 * @param address
	 * @return return the new UserDTO.
	 */
	public UserDTO updateUser(String name, String email, String password, String phone, String address) {
		UserDTO updatedUser;
		try {
			updatedUser = rsl.getHotelManager().updateGuestProfileData(loggedUser.getUserID(), name, email, password, phone, address);
			if(updatedUser == null) return null;
			loggedUser = updatedUser;
			return loggedUser;
		} catch (RemoteException e) {
			return null;
		}
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
    
	/**Update a hotel
	 * @param id Id of the hotel
	 * @param name Name of the hotel
	 * @param location Location of the hotel
	 * @param seasonStart Day where the hotel starts being available
	 * @param seasonEnd Day where the hotel ends being available
	 * @return true if its properly updated
	 */
	public boolean updateHotel(String id, String name, String location, String seasonStart, String seasonEnd) {
		
		try {
    		log.info("Creating new hotel...");
			HotelDTO hotelDTO = rsl.getHotelManager().updateHotel(id, name, location, seasonStart, seasonEnd);
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
    public List<HotelDTO> retrieveHotels() {
    	log.info("Getting list of hotels.");
    	try {
			List<HotelDTO> hotel = rsl.getHotelManager().retrieveHotels();			
			
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
    public List<HotelDTO> retrieveHotels(String arrivalDate) {
    	log.info("Getting list of hotels.");
    	try {
			List<HotelDTO> hotel = rsl.getHotelManager().retrieveHotels(arrivalDate);
			
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
    public boolean cleanHotelsDB() {
    	try {
    		rsl.getHotelManager().cleanHotelsDB();
			return true;
		} catch (RemoteException e) {
			log.fatal("Error cleaning db: " + e.getMessage());
		}
    	return false;
    }
    
    /** Retrieve all the rooms from DB
     * @return An array list of RoomDTO objects
     */
    public ArrayList<RoomDTO> retrieveRooms(){
       	log.info("Getting list of rooms.");
    	try {
			ArrayList<RoomDTO> room = rsl.getHotelManager().retrieveRooms();
			
			if(room != null && room.size() != 0) {
				log.info("List of rooms retrieved succesfully.");
				return room;
			}else {
				log.info("Could not retrieve list of rooms");
			}
    	} catch (RemoteException e) {
    		log.fatal("Error getting list of rooms: " + e.getMessage());
		}
		return null;
    }
    
    /**Retrieve all the rooms from DB by a hotelId
     * @param hotelId Id of the hotel
     * @return An array list of RoomDTO objects
     */
    public ArrayList<RoomDTO> retrieveRoomsByHotelId(String hotelId){
       	log.info("Getting list of rooms.");
    	try {
			ArrayList<RoomDTO> room = rsl.getHotelManager().retrieveRoomsByHotelId(hotelId);
			
			if(room != null && room.size() != 0) {
				log.info("List of rooms retrieved succesfully.");
				return room;
			}else {
				log.info("Could not retrieve list of rooms");
			}
    	} catch (RemoteException e) {
    		log.fatal("Error getting list of rooms: " + e.getMessage());
		}
		return null;
    }
    
    /** Retrieve a room from DB by a roomId
     * @param roomId Id of the room
     * @return A RoomDTO object
     */
    public RoomDTO retrieveRoomById(String roomId){
       	log.info("Retrieving room with ID: " + roomId);
    	try {
			RoomDTO room = rsl.getHotelManager().retrieveRoomById(roomId);
			
			if(room != null){
				log.info("Room retrieved succesfully.");
				return room;
			}else {
				log.info("Could not retrieve a room");
			}
    	} catch (RemoteException e) {
    		log.fatal("Error getting room: " + e.getMessage());
		}
		return null;
    }
    
    
    /** Update the room data to the database
     * @param roomId Id of the room
     * @param size Size of the room
     * @param price price of the room
     * @param roomtype type of the room
     * @param isOccupied true if the room is occupied
     * @return true if its correctly done
     */
    public boolean updateRoom(String roomId, float size, float price, RoomType roomtype, boolean isOccupied) {
    	try {
    		log.info("Updating a room...");
			RoomDTO roomDTO = rsl.getHotelManager().updateRoom(roomId, size, price, roomtype, isOccupied);
			if(roomDTO!=null) {
				log.info("room updated successfully!");
				return true;
			}else {
				log.info("room cannot be updated.");
			}
		} catch (RemoteException e) {
			log.fatal("Error updating a room: " + e.getMessage());
		}
    	return false;
    }
    
    /** Delete a room using the roomID
     * @param id Id of the room
     * @return true if its properly deleted
     */
    public boolean deleteRoom(String id) {
    	try {
    		log.info("Deleting Room with ID: " + id);
			if(rsl.getHotelManager().deleteRoom(id)) {
				log.info("Room deleted successfully!");
				return true;
			}else {
				log.info("Room cannot be deleted");
			}
		} catch (RemoteException e) {
			log.fatal("Error deleting a Room...");
		}
    	return false;
    }
	
    /** Create a new reservation
     * @param reservationId Id of the reservation
     * @param userId Id of the guest
     * @param roomId Id of the room
     * @param firstDay first day the user stays at the hotel
     * @param lastDay last day the user stays at the hotel
     * @return true if its correctly done
     */
    public boolean createReservation(String reservationId, String userId, String roomId, LocalDate firstDay, LocalDate lastDay) {
    	try {
    		log.info("Creating new resevation...");
			ReservationDTO reservationDTO = rsl.getHotelManager().createReservation(reservationId, userId, roomId, firstDay, lastDay);
			if(reservationDTO!=null) {
				log.info("Resevation created successfully!");
				return true;
			}else {
				log.info("Resevation cannot be created.");
			}
		} catch (RemoteException e) {
			log.fatal("Error creating a new resevation: " + e.getMessage());
		}
    	return false;
    }

	/**
	 * Stores a new review in the DB.
	 * @param opinion the text written by the user.
	 * @param score the score that user gives to the hotel.
	 * @param hotelID the hotelID of the hotel the review is form.
	 * @param userID the id of the user that writes the review.
	 * @return A ReviewDTO.
	 */
    public ReviewDTO createReview(String opinion, int score, String hotelID, String userID){
    	try {
			return rsl.getHotelManager().createReview(opinion, score, hotelID, userID);
		
		} catch (RemoteException e) {
			log.fatal("Error creating a new Review: " + e.getMessage());
		}
    	return null;
    }
	/**
	 * Clear the list of the current rooms
	 */
	public void setCurrentRooms() {
		this.currentRooms.clear();
	}
	
	/** Set the current rooms available
	 * @param roomDTO RoomDTO object
	 */
	public void setCurrentRooms(RoomDTO roomDTO) {
		this.currentRooms.add(roomDTO);
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
	
	/**
	 * A method that handles the payment of the reservation depending on the 
	 * 		payment method user selected earlier.
	 * @param arg1 In case of Paypal username, MasterCard cardNumber.
	 * @param arg2 In case of Paypal password, MasterCard cardNumber.
	 * @param amount The amount it cost.
	 * @param type The type of paymentMethods true paypal, false masterCard.
	 * @return {@code true} if the payment has been done successfully, and 
	 * 		{@code false} if not.
	 */
	public boolean payReservation(String arg1, String arg2, float amount, boolean type) {
		try {
			if(type) {
				//Call the method to pay with PayPal
				return rsl.getHotelManager().payPayPal(arg1, arg2, amount);
			}else {
				long cardNumber = Long.parseLong(arg1);
				int securityCode = Integer.parseInt(arg2);
				//Call the method to pay with MasterCard
				return rsl.getHotelManager().payMastercard(cardNumber, securityCode, amount);
			}
		}catch (Exception e) {
			log.fatal("Error making payment: " + e);
		}
		return false;
	}
}
