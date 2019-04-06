package es.deusto.spq.server.data.dto;

import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.User;

public class Assembler {

	public Assembler() {}
	
	// Hotel
	public HotelDTO assembleHotel(Hotel hotel) {
		return new HotelDTO(hotel.getHotelId(), hotel.getName(), hotel.getLocation(), hotel.getServices(),
				hotel.getSeasonStart(), hotel.getSeasonEnding());
	}

	public Hotel disassembleHotel(HotelDTO hotel) {
		return new Hotel(hotel.getHotelId(), hotel.getName(), hotel.getLocation(), hotel.getServices(),
				hotel.getSeasonStart(), hotel.getSeasonEnding());
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
			return null; //TODO return an admin
	}
	
}
