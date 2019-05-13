package es.deusto.spq.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Reservation;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class Assembler {

	public Assembler() {}

	// Hotel
	public HotelDTO assembleHotel(Hotel hotel) {
		if (hotel == null)
			return null;
		HotelDTO hotelDTO = new HotelDTO(hotel.getHotelId(), hotel.getName(), hotel.getLocation(),
				hotel.getSeasonStart(), hotel.getSeasonEnding());
		ServerLogger.getLogger().debug("Assembling hotel...");
		return hotelDTO;
	}

	public Hotel disassembleHotel(HotelDTO hotel) {
		return new Hotel(hotel.getHotelId(), hotel.getName(), hotel.getLocation(), hotel.getSeasonStart(),
				hotel.getSeasonEnding());
	}

	// Room
	public RoomDTO assembleRoom(Room room) {
		if(room == null)
			return null;
		return new RoomDTO(room.getRoomId(), room.getSize(), room.getPrice(), room.getType(), room.isOccupied());
	}

	public Room disassembleRoom(RoomDTO room) {
		return new Room(room.getRoomID(), room.getSize(), room.getPrice(), room.getType(), room.isOccupied());
	}

	// User
	public UserDTO assembleUser(User user) {
		return new UserDTO(user.getUserID(), user.getName(),user instanceof Guest);
	}

	public User disassembleUser(UserDTO user) {
		if(user.isGuest())
			return new Guest(user.getUserID(), user.getName());
		else
			return new Administrator(user.getUserID(), user.getName());
	}

	// Review
	/**
	 * Converts from Review to ReviewDTO
	 *
	 * @param review the Review we want to assembleHotel
	 * @return the ReviewDTO of the assembles Review
	 */
	public ReviewDTO assembleReview(Review review) {
		return new ReviewDTO(review.getReviewID(), review.getOpinion(), review.getScore(), review.getPublishDate(),
				assembleHotel(review.getHotel()), assembleUser(review.getUser()));
	}

	/**
	 * Converts from ReviewDTO to Review
	 *
	 * @param review the ReviewDTO we want to disassemble
	 * @return the Review from the disassembled ReviewDTO
	 */
	public Review disassembleReview(ReviewDTO review) {
		return new Review(review.getReviewID(), review.getOpinion(), review.getScore(), review.getPublishDate(),
				disassembleHotel(review.getHotel()), disassembleUser(review.getUser()));
	}
	
	// Reservation
	public ReservationDTO assembleReservation(Reservation reservation) {
		List<Room> rooms = reservation.getRooms();
		List<RoomDTO> assembledRooms = new ArrayList<RoomDTO>();
		for(Room room : rooms)
			assembledRooms.add(assembleRoom(room));
		return new ReservationDTO(reservation.getReservationID(), 
				reservation.getGuest() == null ? null : reservation.getGuest().getUserID(),
				assembledRooms);
	}
	
	public Reservation disassembleReservation(ReservationDTO reservation) {
		List<RoomDTO> rooms = reservation.getRooms();
		List<Room> disassembledRooms = new ArrayList<Room>();
		for(RoomDTO room : rooms)
			disassembledRooms.add(disassembleRoom(room));
		return new Reservation(reservation.getId(), disassembledRooms);
	}
}
