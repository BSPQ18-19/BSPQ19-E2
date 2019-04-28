package es.deusto.spq.server.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.IHotelDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.gateway.IMastercardGateway;
import es.deusto.spq.server.gateway.IPayPalGateway;
import es.deusto.spq.server.gateway.MastercardGateway;
import es.deusto.spq.server.gateway.PayPalGateway;
import es.deusto.spq.server.logger.ServerLogger;

public class HotelManager extends UnicastRemoteObject implements IHotelManager {

	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private Set<UserDTO> loggedUsers;
	private Logger log;
	private Map<String, Hotel> hotels = new TreeMap<String, Hotel>();
	private IHotelDAO dao;
	
	public HotelManager() throws RemoteException {
		super();
//		new Assembler();
//		this.userDAO = new UserDAO();
//		new HotelDAO();
//		loggedUsers = new HashSet<UserDTO>();
		log = ServerLogger.getLogger();
//		r = new Random();
		payPalGateway = new PayPalGateway();
		mastercardGateway = new MastercardGateway();
		
		/////////////////////////////////////////////////
		
//		LocalDate localDate = LocalDate.of(2019, 04, 01);
//		
//		hotels.put("H01", new Hotel("H01", "Hotel1", "Bilbao", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
//		hotels.put("H02", new Hotel("H02", "Hotel2", "Barcelona", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
//		hotels.put("H03", new Hotel("H03", "Hotel3", "Madrid", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
//		hotels.put("H04", new Hotel("H04", "Hotel4", "Sevilla", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
//		hotels.put("H05", new Hotel("H05", "Hotel5", "Zaragoza", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
//		hotels.put("H06", new Hotel("H06", "Hotel6", "Gijon", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
//		
//		this.dao = new HotelDAO();
//		dao.cleanDB();
//		for(Hotel hotel: hotels.values()) {
//			dao.storeHotel(hotel);
//		}
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
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException {
		ArrayList<HotelDTO> hotelsDTO = new ArrayList<>();
		Assembler hotelAssembler = new Assembler();
		
		log.info("Retrieving hotels...");
		ArrayList<Hotel> listHotels = dao.getHotels();
		log.info(" --> SERVER:");
		log.info("ID: " + listHotels.get(1).getHotelId());
		log.info("NAME: " + listHotels);
		log.info("LOCATION: " + listHotels.get(1).getLocation());
		for(Hotel hotel : listHotels) {
			hotelsDTO.add(hotelAssembler.assemble(hotel));
		}
		log.info("Arraylist size: "+hotelsDTO.size());
		
		if(hotelsDTO.isEmpty()) {
			log.fatal("New exception - There are no hotels for the requested information.");
			throw new RemoteException("HOTELS - There are no hotels for the requested information.");
		}

		return hotelsDTO;
	}

	@Override
	public HotelDTO createHotel(String id, String name, String location, String seasonStart,
			String seasonEnd) throws RemoteException {
		if(hotels.containsKey(id.trim()))
			throw new RemoteException("Server - Hotel ID aready exists");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		LocalDate localDateStart = LocalDate.parse(seasonStart.trim(), formatter);
		LocalDate localDateEnding = LocalDate.parse(seasonEnd.trim(), formatter);
		
		Hotel hotel = new Hotel(id.trim(), name.trim(), location.trim(),
				Timestamp.valueOf(localDateStart.atStartOfDay()), Timestamp.valueOf(localDateEnding.atStartOfDay()));
		hotels.put(hotel.getHotelId(), hotel);
		dao.storeHotel(hotel);
		
		Assembler hotelAssembler = new Assembler();
		return hotelAssembler.assemble(hotel);
	}

	@Override
	public boolean deleteHotel(String id) throws RemoteException {
		hotels.remove(id);
		dao.deleteHotel(id);
		if(hotels.containsKey(id) == false) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean cleanDB() throws RemoteException {
		hotels.clear();
		dao.cleanDB();
		return false;
	}

	/** The PayPal Gateway to interact with. */
	private IPayPalGateway payPalGateway;

	@Override
	public boolean registerPayPal(String username, String password) throws RemoteException {
		if(username == null || username.isEmpty() ||
				password == null || password.isEmpty())
			return false;
		return payPalGateway.registerAccount(username, password);
	}

	@Override
	public boolean registerPayPal(String username, String password, float quantity) throws RemoteException{
		if(username == null || username.isEmpty() || 
				password == null || password.isEmpty() ||
				quantity <= 0)
			return false;
		return payPalGateway.registerAccount(username, password, quantity);
	}

	@Override
	public boolean payPayPal(String username, String password, float quantity) throws RemoteException{
		if(username == null || username.isEmpty() ||
				password == null || password.isEmpty() ||
				quantity <= 0)
			return false;
		return payPalGateway.pay(username, password, quantity);
	}
	
	/** The Mastercard Gateway to interact with. */
	private IMastercardGateway mastercardGateway;
	
	@Override
	public boolean payMastercard(long cardNumber, int securityCode, float amount) throws RemoteException{
		if(cardNumber <= 0 || securityCode <= 0 || amount <= 0)
			return false;
		return mastercardGateway.pay(cardNumber, securityCode, amount);
	}
}
