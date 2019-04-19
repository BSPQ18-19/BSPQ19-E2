package es.deusto.spq.client.controller;

import java.rmi.RemoteException;
import java.sql.Timestamp;
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
	private List<HotelDTO> currentHotels;

	private HotelManagementController() {
		rsl = RMIServiceLocator.getServiceLocator();
		log = ClientLogger.getLogger();
		log.info("HotelManagementController initialized");
		this.currentHotels = new ArrayList<HotelDTO>();
	}

	public static HotelManagementController getController() {
		return controller;
	}

	public UserDTO signInGuest(String name, String email, String password, String phone, String address)
			throws RemoteException {
		log.info("signInGuest called");
		UserDTO result = rsl.getHotelManager().signInGuest(name, email, password, phone, address);
		if (result != null)
			log.info("Signed in user with email: " + email);
		else
			log.warn("Did not signed in user with email: " + email);
		return result;
	}

	public UserDTO logIn(String email, String password) throws RemoteException {
		if (loggedUser != null)
			logOut();
		loggedUser = rsl.getHotelManager().logIn(email, password);
		if (loggedUser != null)
			log.info("Logged in user with email: " + email);
		else
			log.warn("Did not logged in user with email: " + email);
		return loggedUser;
	}

	public boolean logOut() throws RemoteException {
		if (loggedUser == null) {
			log.info("Did not logged out any user - no users were logged");
			return false;
		}
		rsl.getHotelManager().logOut(loggedUser);
		log.info("Logged out user with ID: " + loggedUser.getUserID());
		loggedUser = null;
		return true;
	}

	public boolean createHotel(String id, String name, String location, Timestamp seasonStart, Timestamp seasonEnd) {
		try {
			HotelDTO hotelDTO = rsl.getHotelManager().createHotel(id, name, location, seasonStart, seasonEnd);
			if(hotelDTO == null)
				log.warn("Did not create hotel with ID: " + id);
			else
				log.info("Created hotel with ID: " + id);
			return true;
		} catch (RemoteException e) {
			log.warn(e.getMessage());
		}
		return false;
	}

	public boolean deleteHotel(String id) {
		try {
			if (rsl.getHotelManager().deleteHotel(id)) {
				log.info("Deleted hotel with ID: " + id);
				return true;
			} else {
				log.info("Did not delete the hotel with ID: " + id);
			}
		} catch (RemoteException e) {
			log.fatal("Error deleting an hotel...");
		}
		return false;
	}

	public List<HotelDTO> retrieveHotels() {
		try {
			List<HotelDTO> hotels = rsl.getHotelManager().retrieveHotels();
			if(hotels != null) {
				log.info("Retrieved hotels");
				return hotels;
			} else {
				log.warn("Could not retrieve list of hotels");
			}
		} catch (RemoteException e) {
			log.fatal("Error getting list of hotels: " + e.getMessage());
		}
		return null;
	}

	public List<HotelDTO> getCurrentHotels() {
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
