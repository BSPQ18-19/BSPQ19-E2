package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelManager extends Remote {

	UserDTO signInGuest(String name, String email, String password, String phone, String address) throws RemoteException;
	//TODO signInAdministrator
	UserDTO logIn(String email, String password) throws RemoteException;
	boolean logOut(UserDTO user) throws RemoteException;
	ArrayList<HotelDTO> retrieveHotels() throws RemoteException;
	HotelDTO createHotel(String id, String name, String location, String seasonStart, String seasonEnd) throws RemoteException;
	boolean deleteHotel(String id) throws RemoteException;
	boolean cleanDB() throws RemoteException;
	//TODO getRoomsOfHotelbyID
	//TODO getRoombyID
}
