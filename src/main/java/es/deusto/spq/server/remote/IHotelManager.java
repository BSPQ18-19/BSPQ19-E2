package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelManager extends Remote {

	public UserDTO signInGuest(String name, String email, String password, String phone, String address) throws RemoteException;
	//TODO signInAdministrator
	public UserDTO logIn(String email, String password) throws RemoteException;
	public boolean logOut(UserDTO user) throws RemoteException;
	public List<HotelDTO> getHotels(UserDTO authorization) throws RemoteException;
	public HotelDTO getHotelbyID(UserDTO authorization, String hotelID) throws RemoteException;
	public HotelDTO createHotel(UserDTO authorization, HotelDTO hotel) throws RemoteException;
	public boolean deleteHotel(UserDTO authorization, String ID) throws RemoteException;
	//TODO editHotel
	//TODO getRoomsOfHotelbyID
	//TODO getRoombyID
}