package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelManager extends Remote {

	public UserDTO signInGuest(String name, String email, String password, String phone, String address);
	//TODO signInAdministrator
	public UserDTO logIn(String email, String password);
	public boolean logOut(UserDTO user);
	public List<HotelDTO> getHotels(UserDTO authorization);
	public HotelDTO getHotelbyID(UserDTO authorization, String hotelID);
	public HotelDTO createHotel(UserDTO authorization, HotelDTO hotel);
	public boolean deleteHotel(UserDTO authorization, String ID);
	//TODO editHotel
	//TODO getRoomsOfHotelbyID
	//TODO getRoombyID
}
