package es.deusto.spq.server.data.dto;

import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class Assembler {

	public Assembler() {}
	
	// Hotel
	public HotelDTO assemble(Hotel hotel) {
		if (hotel == null)
			return null;
		HotelDTO hotelDTO = new HotelDTO(hotel.getHotelId(), hotel.getName(), hotel.getLocation(),
				hotel.getSeasonStart(), hotel.getSeasonEnding());
		ServerLogger.getLogger().info("Assembling hotel...");
		return hotelDTO;
	}

	public Hotel dissasembleHotel(HotelDTO hotel) {
		return new Hotel(hotel.getHotelId(), hotel.getName(), hotel.getLocation(), hotel.getSeasonStart(),
				hotel.getSeasonEnding());
	}

	// Room
	public RoomDTO assembleRoom(Room room) {
		return new RoomDTO(room.getRoomId(), room.getSize(), room.getPrice(), room.getType(), room.isOccupied(),
				room.getFeatures());
	}
	
	public Room disassembleRoom(RoomDTO room) {
		return new Room(room.getRoomID(), room.getSize(), room.getPrice(), room.getType(), room.isOccupied(),
				room.getFeatures());
	}
	
	// User
	public UserDTO assembleUser(User user) {
		return new UserDTO(user.getUserID(), user.getName(), user instanceof Guest);
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
	 * @param review the Review we want to assemble
	 * @return the ReviewDTO of the assembles Review
	 */
	public ReviewDTO assembleReview(Review review) {
		return new ReviewDTO(review.getReviewID(), review.getOpinion(), review.getScore(), review.getPublishDate(),
				assemble(review.getHotel()), assembleUser(review.getUser()));
	}

	/**
	 * Converts from ReviewDTO to Review
	 *
	 * @param review the ReviewDTO we want to disassemble
	 * @return the Review from the disassembled ReviewDTO
	 */
	public Review disassembleReview(ReviewDTO review) {
		return new Review(review.getReviewID(), review.getOpinion(), review.getScore(), review.getPublishDate(),
				dissasembleHotel(review.getHotel()), disassembleUser(review.getUser()));
	}
}
