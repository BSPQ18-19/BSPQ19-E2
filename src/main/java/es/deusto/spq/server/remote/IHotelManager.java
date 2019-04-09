package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelManager extends Remote {

	public UserDTO signInGuest(String name, String email, String password, String phone, String address) throws RemoteException;
	//TODO signInAdministrator
	public UserDTO logIn(String email, String password) throws RemoteException;
	public boolean logOut(UserDTO user) throws RemoteException;
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException;
	public HotelDTO createHotel(String id, String name, String location, String seasonStart, String seasonEnd) throws RemoteException;
	public boolean deleteHotel(String id) throws RemoteException;
	public boolean cleanDB() throws RemoteException;
	//TODO getRoomsOfHotelbyID
	//TODO getRoombyID
}
