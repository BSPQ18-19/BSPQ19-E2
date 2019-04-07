package es.deusto.spq.client.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	
public boolean createHotel(String id, String name, String location, String[] services, String seasonStart, String seasonEnd) {
    	
    	try {
    		System.out.println("Creating new hotel...");
			HotelDTO hotelDTO = rsl.getHotelManager().createHotel(id, name, location, services, seasonStart, seasonEnd);
			if(hotelDTO!=null) {
				System.out.println("Hotel created successfully!");
				return true;
			}else {
				System.out.println("Hotel cannot be created.");
			}
		} catch (RemoteException e) {
			System.out.println("Error creating a new hotel: " + e.getMessage());
		}
    	return false;
    }
    
    public boolean deleteHotel(String id) {
    	try {
			System.out.println("Deleting hotel with ID: " + id);
			if(rsl.getHotelManager().deleteHotel(id)) {
				System.out.println("Hotel deleted successfully!");
				return true;
			}else {
				System.out.println("Hotel cannot be deleted");
			}
		} catch (RemoteException e) {
			System.out.println("Error deleting an hotel...");
		}
    	return false;
    }
    
    public ArrayList<HotelDTO> retrieveHotels() {
    	System.out.println("Getting list of hotels.");
    	try {
			ArrayList<HotelDTO> hotel = rsl.getHotelManager().retrieveHotels();
//			for(HotelDTO hotelDTO: hotel) {
//				System.out.println(hotelDTO.getLocation());
//			}
//			HotelDTO[] hotels = hotel.toArray(new HotelDTO[hotel.size()]);
			
			
			if(hotel != null && hotel.size() != 0) {
				System.out.println("List of hotels retrieved succesfully.");
				return hotel;
			}else {
				System.out.println("Could not retrieve list of hotels");
			}
    	} catch (RemoteException e) {
			System.out.println("Error getting list of hotels: " + e.getMessage());
		}
		return null;
    }
    
    public boolean cleanDB() {
    	try {
    		rsl.getHotelManager().cleanDB();
			return true;
		} catch (RemoteException e) {
			System.out.println("Error cleaning db: " + e.getMessage());
		}
    	return false;
    }
    
	public ArrayList<HotelDTO> getCurrentHotels() {
		return currentHotels;
	}
	public void setCurrentHotels(HotelDTO hotelDTO) {
		this.currentHotels.add(hotelDTO);
	}
	public void setCurrentHotels() {
		this.currentHotels.clear();
	}
	
	public UserDTO getLoggedUser() {
		return loggedUser;
	}

}