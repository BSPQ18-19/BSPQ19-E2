package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelManager extends Remote {

	public boolean signInGuest(String name, String email, String password, int phone, String address);
	//TODO signInAdministrator
	public UserDTO logIn(String email, String password);
	public boolean logOut(UserDTO user);
	public boolean createHotel(HotelDTO hotel);
	public boolean editHotel(String ID, HotelDTO newVersion);
	public boolean deleteHotel(String ID);
	public List<HotelDTO> getHotels();
	public HotelDTO getHotelbyID(String hotelID);
	public List<RoomDTO> getRoomOfHotelID(String hoteID);
	public RoomDTO getRoombyID(String roomID);
	
}
