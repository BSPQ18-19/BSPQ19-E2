package es.deusto.spq.client.controller;

import java.util.List;
import java.util.logging.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public class HotelManagementController {

	private static HotelManagementController controller;
	private RMIServiceLocator rsl;
	private UserDTO loggedUser = null;
	private Logger log;
	
	static {
		controller = new HotelManagementController();
	}
	
	private HotelManagementController() {
		rsl = RMIServiceLocator.getServiceLocator();
		log = ClientLogger.getLogger();
	}
	
	public static HotelManagementController getController() {
		return controller;
	}
	
	public UserDTO signInGuest(String name, String email, String password, String phone, String address) {
		UserDTO result = rsl.getHotelManager().signInGuest(name, email, password, phone, address);
		if(result != null)
			log.info("Signed in user with email: " + email);
		else
			log.info("Did not signed in user with email: " + email);
		return result;
	}
	
	public void logIn(String email, String password) {
		if(loggedUser != null)
			logOut();
		loggedUser = rsl.getHotelManager().logIn(email, password);
		if(loggedUser != null)
			log.info("Did not logged in user with email: " + email);
		else
			log.info("Logged in user with email: " + email);
	}
	
	public boolean logOut() {
		if(loggedUser == null) {
			log.info("Did not logged out any user - no users were logged");
			return false;
		}
		rsl.getHotelManager().logOut(loggedUser);
		log.info("Logged out user with ID: " + loggedUser.getUserID());
		loggedUser = null;
		return true;
	}
	
	public HotelDTO createHotel(HotelDTO hotel) {
		HotelDTO result = rsl.getHotelManager().createHotel(loggedUser, hotel);
		if(result != null)
			log.info("Created hotel with ID: " + hotel.getHotelId());
		else
			log.info("Did not created hotel with ID: " + hotel.getHotelId() + " - and name: " + hotel.getName());
		return result;
	}
	
//	public boolean editHotel(String ID, HotelDTO newVersion) {
//		boolean result = rsl.getHotelManager().editHotel(ID, newVersion);
//		if(result)
//			log.info("Modified hotel with ID: " + ID);
//		else
//			log.info("Did not modified hotel with ID: " + ID);
//		return result;
//	}
	
	public boolean deleteHotel(String ID) {
		boolean result = rsl.getHotelManager().deleteHotel(loggedUser, ID);
		if(result)
			log.info("Deleted hotel with ID: " + ID);
		else
			log.info("Did not delete hotel with ID: " + ID);
		return result;
	}
	
	public List<HotelDTO> getHotels(){
		List<HotelDTO> result = rsl.getHotelManager().getHotels(loggedUser);
		log.info("List of hotels retrieved");
		return result;
	}
	
	public HotelDTO getHotelbyID(String hotelID) {
		HotelDTO result = rsl.getHotelManager().getHotelbyID(loggedUser, hotelID);
		if(result != null)
			log.info("Retrieved hotel with ID: " + hotelID);
		else
			log.info("Did not retrieve hotel with ID: " + hotelID);
		return result;
	}
	
//	public List<RoomDTO> getRoomOfHotelID(String hotelID){
//		List<RoomDTO> rooms = rsl.getHotelManager().getRoomOfHotelID(hotelID);
//		if(rooms != null)
//			log.info("Retrieved rooms from hotel with ID: " + hotelID);
//		else
//			log.info("Did not retrieve rooms from hotel with ID: " + hotelID);
//		return rooms;
//	}
//	
//	public RoomDTO getRoombyID(String roomID) {
//		RoomDTO room = rsl.getHotelManager().getRoombyID(roomID);
//		if(room != null)
//			log.info("Retrieved room with ID: " + roomID);
//		else
//			log.info("Did not retrieve room with ID: " + roomID);
//		return room;
//	}

}
