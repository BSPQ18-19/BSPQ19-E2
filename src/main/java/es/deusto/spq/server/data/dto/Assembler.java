package es.deusto.spq.server.data.dto;

import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.User;

public class Assembler {

	public HotelDTO assembleHotel(Hotel hotel) {
		return new HotelDTO(hotel.getHotelId(), hotel.getName(), hotel.getLocation(), hotel.getServices(),
				hotel.getSeasonStart(), hotel.getSeasonEnding());
	}

	public Hotel disassembleHotel(HotelDTO hotel) {
		return new Hotel(hotel.getHotelId(), hotel.getName(), hotel.getLocation(), hotel.getServices(),
				hotel.getSeasonStart(), hotel.getSeasonEnding());
	}

	public RoomDTO assembleRoom(Room room) {
		return new RoomDTO(room.getRoomId(), room.getSize(), room.getPrice(), room.getType(), room.isOccupied(),
				room.getFeatures());
	}
	
	public Room disassembleRoom(RoomDTO room) {
		return new Room(room.getRoomID(), room.getSize(), room.getPrice(), room.getType(), room.isOccupied(),
				room.getFeatures());
	}
	
	public UserDTO disassembleUser(User user) {
		return new UserDTO(user.getUserID(), user.getName());
	}
	
}
