package es.deusto.spq.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.deusto.spq.server.data.SignupException;
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
	//TODO All the DAOs
	private Set<User> loggedUsers;
	private Logger log;
	
	protected HotelManager() throws RemoteException {
		super();
		this.assembler = new Assembler();
		this.userDAO = new UserDAO();
		loggedUsers = new HashSet<User>();
		log = ServerLogger.getLogger();
	}
	
	@Override
	public boolean signInGuest(String name, String email, String password, int phone, String address) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int logIn(String userID, String password) {
		User result = userDAO.getUserbyID(userID);
		if(result != null && result.getPassword().equals(password)) {
			if(result instanceof Guest) {
				loggedUsers.add(result);
				return 2;
			}/*else if(result instanceof Admin) {
				loggedUsers.add(result);
				return 1;
			}*/
		}
		return 3;
	}

	@Override
	public boolean logOut(UserDTO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createHotel(HotelDTO hotel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editHotel(String ID, HotelDTO newVersion) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteHotel(String ID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<HotelDTO> getHotels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HotelDTO getHotelbyID(String hotelID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoomDTO> getRoomOfHotelID(String hoteID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoomDTO getRoombyID(String roomID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean registerGuest(String email, String name, String password, String phone, String address) throws SignupException {

		if (email.trim().isEmpty()
				|| password.trim().isEmpty()
				|| phone.trim().isEmpty()
				|| address.trim().isEmpty()) {

			throw new SignupException(SignupException.VALIDATION);
		}

		// TODO save phone number (after it gets migrated to String)
		User placeholder = new Guest(null, name, email, password, 1, address);

		User result = userDAO.createUser(placeholder);

		if (result == null) {
			throw new SignupException();
		}

		ServerLogger.getLogger().info("Registered new Guest: " + result.toString());
		return true;
	}

}
