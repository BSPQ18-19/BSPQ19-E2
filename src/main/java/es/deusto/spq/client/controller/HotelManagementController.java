package es.deusto.spq.client.controller;

import java.rmi.RemoteException;
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
	
	private HotelManagementController() {
		rsl = RMIServiceLocator.getServiceLocator();
		log = ClientLogger.getLogger();
		log.info("HotelManagementController initialized");
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
	
	public HotelDTO createHotel(HotelDTO hotel) throws RemoteException {
		HotelDTO result = rsl.getHotelManager().createHotel(loggedUser, hotel);
		if(result != null)
			log.info("Created hotel with ID: " + hotel.getHotelId());
		else
			log.info("Did not created hotel with ID: " + hotel.getHotelId() + " - and name: " + hotel.getName());
		return result;
	}
	
	public boolean deleteHotel(String ID) throws RemoteException {
		boolean result = rsl.getHotelManager().deleteHotel(loggedUser, ID);
		if(result)
			log.info("Deleted hotel with ID: " + ID);
		else
			log.info("Did not delete hotel with ID: " + ID);
		return result;
	}
	
	public List<HotelDTO> getHotels() throws RemoteException {
		List<HotelDTO> result = rsl.getHotelManager().getHotels(loggedUser);
		log.info("List of hotels retrieved");
		return result;
	}
	
	public HotelDTO getHotelbyID(String hotelID) throws RemoteException {
		HotelDTO result = rsl.getHotelManager().getHotelbyID(loggedUser, hotelID);
		if(result != null)
			log.info("Retrieved hotel with ID: " + hotelID);
		else
			log.info("Did not retrieve hotel with ID: " + hotelID);
		return result;
	}
	
	public UserDTO getLoggedUser() {
		return loggedUser;
	}

}