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
import es.deusto.spq.server.data.dao.IReservationDAO;
import es.deusto.spq.server.data.dao.IReviewDAO;
import es.deusto.spq.server.data.dao.IRoomDAO;

import es.deusto.spq.server.data.dao.ReviewDAO;
import es.deusto.spq.server.data.dao.ReservationDAO;
import es.deusto.spq.server.data.dao.ReviewDAO;
import es.deusto.spq.server.data.dao.RoomDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.HotelDTO;

import es.deusto.spq.server.data.dto.ReviewDTO;
import es.deusto.spq.server.data.dto.ReservationDTO;
import es.deusto.spq.server.data.dto.ReviewDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;

import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.Reservation;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.RoomType;
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
	private ArrayList<Room> rooms1, rooms2;
	private IHotelDAO hotelDao;
	private IRoomDAO roomDao;
	private IReservationDAO reservationDao;
	private IReviewDAO reviewDAO;
	
	public HotelManager() throws RemoteException {
		super();
		new Assembler();
		this.userDAO = new UserDAO();
		this.roomDao = new RoomDAO();
		this.reviewDAO = new ReviewDAO();
		this.reservationDao = new ReservationDAO();

		this.reviewDAO = new ReviewDAO();
		loggedUsers = new HashSet<UserDTO>();
		log = ServerLogger.getLogger();
		r = new Random();
		payPalGateway = new PayPalGateway();
		mastercardGateway = new MastercardGateway();
		
		
		
		
		userDAO.createUser(new Guest("u1", "a", "a", "a", "a", "a"));
		
		
		
		this.rooms1 = new ArrayList<>();
		this.rooms2 = new ArrayList<>();
		
		rooms1.add(new Room("R01", 250, 150, RoomType.DOUBLE, false));
		rooms1.add(new Room("R02", 450, 400, RoomType.SUITE, false));
		rooms1.add(new Room("R03", 350, 400, RoomType.TRIPLE, false));
		for(Room r : rooms1)
			roomDao.createRoom(r);
		rooms2.add(new Room("R05", 250, 150, RoomType.DOUBLE, false));
		rooms2.add(new Room("R06", 250, 150, RoomType.DOUBLE, false));
		rooms2.add(new Room("R07", 250, 150, RoomType.DOUBLE, false));
		rooms2.add(new Room("R08", 200, 100, RoomType.SINGLE, false));
		for(Room r : rooms2)
			roomDao.createRoom(r);
		
		hotels.put("H01", new Hotel("H01", "Hotel1", "Bilbao", Timestamp.valueOf(LocalDate.of(2019, 04, 01).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2019, 12, 31).atStartOfDay())));
		hotels.put("H02", new Hotel("H02", "Hotel2", "Barcelona", Timestamp.valueOf(LocalDate.of(2019, 06, 01).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2019, 9, 30).atStartOfDay())));
		hotels.put("H03", new Hotel("H03", "Hotel3", "Madrid", Timestamp.valueOf(LocalDate.of(2019, 04, 15).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2019, 06, 20).atStartOfDay())));
		hotels.put("H04", new Hotel("H04", "Hotel4", "Sevilla", Timestamp.valueOf(LocalDate.of(2019, 05, 01).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2019, 10, 01).atStartOfDay())));
		hotels.put("H05", new Hotel("H05", "Hotel5", "Zaragoza", Timestamp.valueOf(LocalDate.of(2019, 05, 14).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2019, 11, 30).atStartOfDay())));
		hotels.put("H06", new Hotel("H06", "Hotel6", "Gijon", Timestamp.valueOf(LocalDate.of(2019, 04, 20).atStartOfDay()), Timestamp.valueOf(LocalDate.of(2019, 11, 30).atStartOfDay())));
		
		this.hotelDao = new HotelDAO();
		hotels.get("H01").setListRooms(rooms1);
		hotels.get("H02").setListRooms(rooms2);
		for(Hotel hotel: hotels.values()) {
			hotelDao.storeHotel(hotel);
		}
		
		//The default admin because only one admin can register another
		User defaultAdmin = new  Administrator("DEFAULT", "admin", "admin", "admin", "admin");
		userDAO.deleteUserbyID(defaultAdmin.getUserID());
		userDAO.createUser(defaultAdmin);

		//PayPal account for the payment
		registerPayPal("iker-barriocanal","123", 10000);
		registerPayPal("iapellaniz","deusto", 15000);
		registerPayPal("Egoes98","7954", 9560);
		registerPayPal("Arribas12","arribas12", 20000);
		registerPayPal("dusekvojtech","deusto2019", 10100);
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
	public UserDTO signInAdmin(String name, String email, String password, String address)
			throws RemoteException {
		String randomID = generateRandomId();
		log.info("Selected random ID for new user: " + randomID);
		User user = new Administrator(randomID, name, email, password, address);
		return userDAO.createUser(user);
	}

	@Override
	public UserDTO logIn(String email, String password) throws RemoteException {
		log.debug("Trying to login: " + email);
		UserDTO user = userDAO.logIn(email, password);
		if(user == null)
			log.debug("user is null");
		else
			log.debug("UserDAO finished: " + user.getName());
		loggedUsers.add(user);
		return user;
	}

	@Override
	public boolean logOut(UserDTO user) throws RemoteException{
		return loggedUsers.remove(user);
	}

	@Override
	public UserDTO updateGuestProfileData(String userId, String name, String email, String password, String phone,
			String address) throws RemoteException {
		if(userId == null) return null;
		//Return the updatedUser
		UserDTO updatedUser = userDAO.updateGuest(userId, name, email, password, phone, address);
		//Searches on the loggedUsers list the actual user(Without the update)
		UserDTO actualUser = null;
		for(UserDTO userDTO : loggedUsers) {
			if(userDTO.getUserID().equals(userId)) {
				actualUser = userDTO;
				break;
			}
		}
		//Deletes the User from logged users and replace it with the one updated
		loggedUsers.remove(actualUser);
		loggedUsers.add(updatedUser);
		return updatedUser;
	}

	@Override
	public List<HotelDTO> retrieveHotels() throws RemoteException {
		List<HotelDTO> hotelsDTO = new ArrayList<>();
		Assembler hotelAssembler = new Assembler();
		
		List<Hotel> listHotels = hotelDao.getHotels();
		for(Hotel hotel : listHotels) {
			hotelsDTO.add(hotelAssembler.assembleHotel(hotel));
		}
		
		if(hotelsDTO.isEmpty()) {
			log.fatal("New exception - There are no hotels for the requested information.");
			throw new RemoteException("HOTELS - There are no hotels for the requested information.");
		}

		return hotelsDTO;
	}
	
	@Override
	public List<HotelDTO> retrieveHotels(String arrivalDate) throws RemoteException {
		ArrayList<HotelDTO> hotelsDTO = new ArrayList<>();
		Assembler hotelAssembler = new Assembler();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		LocalDate localDateStart = LocalDate.parse(arrivalDate.trim(), formatter);
		
		log.info("Retrieving hotels...");
		List<Hotel> listHotels = hotelDao.getHotels(Timestamp.valueOf(localDateStart.atStartOfDay()));
		for(Hotel hotel : listHotels) {
			hotelsDTO.add(hotelAssembler.assembleHotel(hotel));
		}
		
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
		hotelDao.storeHotel(hotel);
		
		Assembler hotelAssembler = new Assembler();
		return hotelAssembler.assembleHotel(hotel);
	}

	@Override
	public HotelDTO updateHotel(String id, String name, String location, String seasonStart,
			String seasonEnd) throws RemoteException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		LocalDate localDateStart = LocalDate.parse(seasonStart.trim(), formatter);
		LocalDate localDateEnding = LocalDate.parse(seasonEnd.trim(), formatter);
		
		Hotel hotel = new Hotel(id, name, location, 
				Timestamp.valueOf(localDateStart.atStartOfDay()), Timestamp.valueOf(localDateEnding.atStartOfDay()));
		for(Hotel hotelAux : hotels.values()) {
			if(hotelAux.getHotelId().equals(hotel.getHotelId())) {
				hotels.replace(hotelAux.getHotelId(), hotel);
			}
		}
		hotelDao.updateHotel(hotel);

		Assembler hotelAssembler = new Assembler();
		return hotelAssembler.assembleHotel(hotel);
	}
	
	@Override
	public boolean deleteHotel(String id) throws RemoteException {
		hotels.remove(id);
		hotelDao.deleteHotel(id);
		if(hotels.containsKey(id) == false) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean cleanHotelsDB() throws RemoteException {
		hotels.clear();
		hotelDao.cleanHotelsDB();
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
	
	
	@Override
	public ArrayList<RoomDTO> retrieveRooms() throws RemoteException {
		ArrayList<RoomDTO> roomsDTO = new ArrayList<>();
		Assembler roomAssembler = new Assembler();
		
		List<Room> listRooms = roomDao.getRooms();
		for(Room room : listRooms) {
			roomsDTO.add(roomAssembler.assembleRoom(room));
		}
		
		if(roomsDTO.isEmpty()) {
			log.fatal("New exception - There are no rooms for the requested information.");
			throw new RemoteException("ROOMS - There are no rooms for the requested information.");
		}
	
		return roomsDTO;
	}

	@Override
	public RoomDTO updateRoom(String roomId, float size, float price, RoomType roomtype, boolean isOccupied) throws RemoteException {
		
		log.debug("HotelManager is going to update a room");
		
		Room room = new Room(roomId, size, price, roomtype, isOccupied);
		roomDao.updateRoom(room);
		
		Assembler roomAssembler = new Assembler();
		
		return roomAssembler.assembleRoom(room);
	}

	@Override
	public boolean deleteRoom(String id) throws RemoteException {
		for(int i = 0; i < rooms1.size(); i++) {
			if(rooms1.get(i).getRoomId().equals(id)) {
				rooms1.remove(i);
				roomDao.deleteRoom(id);
				return true;
			}	
		}
		for(int i = 0; i < rooms2.size(); i++) {
			if(rooms2.get(i).getRoomId().equals(id)) {
				rooms2.remove(i);
				roomDao.deleteRoom(id);
				return true;
			}
				
		}
		return false;
	}

	@Override
	public ArrayList<RoomDTO> retrieveRoomsByHotelId(String hotelID) throws RemoteException {
		ArrayList<RoomDTO> roomsDTO = new ArrayList<>();
		Assembler roomAssembler = new Assembler();
		
		List<Room> listRooms = roomDao.getRoomByHotelId(hotelID);

		for(Room room : listRooms) {
			roomsDTO.add(roomAssembler.assembleRoom(room));
		}

		if(roomsDTO.isEmpty()) {
			log.fatal("New exception - There are no rooms for the requested information.");
			throw new RemoteException("ROOMS - There are no rooms for the requested information.");
		}

		return roomsDTO;
	}

	@Override
	public List<ReviewDTO> retrieveReviews(String hotelId) throws RemoteException {
		List<ReviewDTO> reviewsDTO = new ArrayList<>();
		Assembler reviewAssembler = new Assembler();

		List<Review> listReviews = reviewDAO.getReviewsOfHotel(hotelId);
		if(listReviews.isEmpty() || listReviews == null) {
			return null;
		}
		for(Review r : listReviews) {
			reviewsDTO.add(reviewAssembler.assembleReview(r));
		}
		return reviewsDTO;
	}

	public RoomDTO retrieveRoomById(String roomID) throws RemoteException {
		Assembler roomAssembler = new Assembler();
		
		Room room = roomDao.getRoomById(roomID);
		
		log.debug("Retrieving room with ID: " + roomID + " - " + room == null ? null : room.getRoomId());
		
		RoomDTO roomDto = roomAssembler.assembleRoom(room);
		
		if(roomDto == null) {
			log.fatal("New exception - There is no room for the requested information.");
			throw new RemoteException("ROOMS - There is no room for the requested information.");
		}
		
		return roomDto;
	}

	@Override
	public ReservationDTO createReservation(String reservationId, String userId, String roomId, LocalDate firstDay, LocalDate lastDay)
			throws RemoteException {
		Assembler reservationAssembler = new Assembler();
		
		Reservation reservation = new Reservation(reservationId, userId, roomId,
				Timestamp.valueOf(firstDay.atStartOfDay()), Timestamp.valueOf(lastDay.atStartOfDay()));
		reservationDao.createReservation(reservation);
		return reservationAssembler.assembleReservation(reservation);
	}

	@Override
	public ReviewDTO createReview(String opinion, int score, String hotelID, String userID) throws RemoteException {
		//Generate the ID for reviews.
		String reviewID = userID+"-"+hotelID;
		Assembler reviewAssembler = new Assembler();

		//The date for review, the actual date.
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		//Gets the hotel and user related to the review.
		Hotel h = hotelDao.getHotel(hotelID);
		User u = userDAO.getUser(userID);

		//Created the review to be stored.
		Review r = new Review(reviewID, opinion, score, timestamp);
		r.setHotel(h);
		r.setUser(u);

		//Stores the review.
		Review detachedCopy = reviewDAO.storeReview(r);
		if(detachedCopy == null) {
			return null;
		}

		//Creates the ReviewDT= to be returned.
		ReviewDTO rDTO = reviewAssembler.assembleReview(detachedCopy);
		return rDTO;
	}

}
