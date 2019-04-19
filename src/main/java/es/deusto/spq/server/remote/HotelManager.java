package es.deusto.spq.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.dao.DAO;
import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.IHotelDAO;
import es.deusto.spq.server.data.dao.IUserDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class HotelManager extends UnicastRemoteObject implements IHotelManager {

	private static final long serialVersionUID = 1L;
	/** The DAO to be used to manage users */
	private IUserDAO userDAO = new UserDAO();
	/** The DAO to be used to manage hotels */
	private IHotelDAO hotelDAO = new HotelDAO();
	/** A set to store the IDs of the current logged users */
	private Set<String> loggedUsers = new HashSet<String>(); //IDs
	/** A set to store the IDs of the created hotels */
	private Set<String> createdHotels = new HashSet<String>();//IDs
	/** The logger to log to */
	private Logger log = ServerLogger.getLogger();
	/** The assembler to assemble and disassembler the data model */
	private Assembler assembler = new Assembler();
	
	/**
	 * Creates a new instance of the HotelManager.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public HotelManager() throws RemoteException {
		super();
		r = new Random();
		
		//Clear and reload the database
		setUpDataBase();
	}
	
	/**
	 * Sets up the database: clean and load hotels and users.
	 */
	private void setUpDataBase() {
		DataLoader.cleanDataBase((DAO) userDAO);
		createdHotels = DataLoader.loadHotels(hotelDAO);
		DataLoader.loadUsers();
		log.debug("Database set up completed");
	}

	private Random r;
	private String generateRandomId() {//TODO make a proper ID generator
		return Integer.toString(r.nextInt(Integer.MAX_VALUE));
	}
	
	private boolean checkAuthorization(UserDTO userDTO) { //TODO
		return true;
	}
	
	@Override
	public UserDTO signInGuest(String name, String email, String password, String phone, 
			String address) throws RemoteException {
		if(email == null || email.isEmpty() || password == null || password.isEmpty()) {
			log.warn("Could not signed in guest, email or password are null or empty.");
			throw new RemoteException("Email or password are null or empty.");
		}
		String randomID = generateRandomId();
		User result = userDAO.createUser(new Guest(randomID, name, email, password, phone, address));
		log.info("Created new Guest with ID: " + randomID);
		return assembler.assembleUser(result);
	}

	@Override
	public UserDTO logIn(String email, String password) throws RemoteException {
		User result = userDAO.logIn(email, password);
		loggedUsers.add(result.getUserID());
		log.info("Logged in user with email: " + email);
		return assembler.assembleUser(result);
	}

	@Override
	public boolean logOut(UserDTO user) throws RemoteException{
		boolean result = loggedUsers.remove(user.getUserID());
		if(result)
			log.info("Logged out user with ID: " + user.getUserID());
		return result;
	}

	@Override
	public List<HotelDTO> retrieveHotels() throws RemoteException {
		List<Hotel> hotels = hotelDAO.getHotels();
		List<HotelDTO> result = assembler.assemble(hotels);
		log.info("Retrieving all the hotels");
		return result;
	}


	@Override
	public HotelDTO createHotel(String id, String name, String location, Timestamp seasonStart,
			Timestamp seasonEnd) throws RemoteException {
		
		if(id == null || id.isEmpty()) {
			log.warn("Could not create hotel, ID is null or empty");
			throw new RemoteException("The ID is null or empty.");
		}
		
		if(createdHotels.contains(id)) {//TODO do this properly: to the database
			log.warn("Could not create hotel, ID is duplicated");
			throw new RemoteException("The ID is duplicated");
		}
		
		Hotel result = hotelDAO.createHotel(new Hotel(id, name, location, seasonStart, seasonEnd));
		log.info("Hotel created with ID: " + id);
		return assembler.assemble(result);
	}


	@Override
	public boolean deleteHotel(String id) throws RemoteException {
		if(createdHotels.contains(id)) {
			hotelDAO.deleteHotelbyID(id);
			createdHotels.remove(id);
			return true;
		}
		return false;
	}

}
