package es.deusto.spq.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class HotelManager extends UnicastRemoteObject implements IHotelManager {

	private static final long serialVersionUID = 1L;
	private Assembler assembler;
	private UserDAO userDAO;
	private HotelDAO hotelDAO;
	private Set<UserDTO> loggedUsers;
	private Logger log;
	
	public HotelManager() throws RemoteException {
		super();
		this.assembler = new Assembler();
		this.userDAO = new UserDAO();
		this.hotelDAO = new HotelDAO();
		loggedUsers = new HashSet<UserDTO>();
		log = ServerLogger.getLogger();
		r = new Random();
	}

	private Random r;
	private String generateRandomId() {
		return Integer.toString(r.nextInt(Integer.MAX_VALUE));
	}
	
	@Override
	public UserDTO signInGuest(String name, String email, String password, String phone, String address) throws RemoteException{
		String randomID = generateRandomId();
		log.info("Selected random ID for new user: " + randomID);
		User user = new Guest(randomID, name, email, password, phone, address); //TODO generate correctly the IDs
		return userDAO.createUser(user);
	}

	@Override
	public UserDTO logIn(String email, String password) throws RemoteException{
		UserDTO user = userDAO.logIn(email, password);
		loggedUsers.add(user);
		return user;
	}

	@Override
	public boolean logOut(UserDTO user) throws RemoteException{
		return loggedUsers.remove(user);
	}

	@Override
	public List<HotelDTO> getHotels(UserDTO authorization) throws RemoteException{
		return hotelDAO.getHotels(authorization);
	}

	@Override
	public HotelDTO getHotelbyID(UserDTO authorization, String hotelID) throws RemoteException{
		return hotelDAO.getHotelbyID(authorization, hotelID);
	}

	@Override
	public HotelDTO createHotel(UserDTO authorization, HotelDTO hotel) throws RemoteException{
		return hotelDAO.createHotel(authorization, hotel);
	}

	@Override
	public boolean deleteHotel(UserDTO authorization, String ID) throws RemoteException{
		return hotelDAO.deleteHotel(authorization, ID);
	}
	
}