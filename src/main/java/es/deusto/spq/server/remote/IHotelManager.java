package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelManager extends Remote {

	/**
	 * Signs in the guest to the system; i.e. adds the user to the database.
	 * @param name The username of the new Guest.
	 * @param email The email of the new Guest.
	 * @param password The password of the new Guest.
	 * @param phone The phone number of the new Guest.
	 * @param address The home address of the new Guest.
	 * @return A {@link es.deusto.spq.server.data.dto.UserDTO} of the signed up Guest.
	 * 			If any of the email or password given is null, returns null.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public UserDTO signInGuest(String name, String email, String password, String phone, 
			String address) throws RemoteException;
	//TODO signInAdministrator
	/**
	 * Logs in the user.
	 * @param email Email of the user to be logged in.
	 * @param password The password of the user to be logged in.
	 * @return A {@link es.deusto.spq.server.data.dto.UserDTO} with the logged user.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public UserDTO logIn(String email, String password) throws RemoteException;
	/**
	 * Logs out the user.
	 * @param user The {@link es.deusto.spq.server.data.dto.UserDTO} of the user to be logged out.
	 * @return True if the user has successfully logged out, and False if not.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public boolean logOut(UserDTO user) throws RemoteException;
	/**
	 * Retrieves all the hotels in the database.
	 * @return A List of HotelDTO.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public List<HotelDTO> retrieveHotels() throws RemoteException;
	/**
	 * Creates the Hotel in the database.
	 * @param id The ID of the Hotel to be created.
	 * @param name The name of the Hotel to be created.
	 * @param location The location of the Hotel to be created.
	 * @param seasonStart The starting date of the season.
	 * @param seasonEnd The ending date of the season-
	 * @return A {@link es.deusto.spq.server.data.dto.HotelDTO} of the created Hotel.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public HotelDTO createHotel(String id, String name, String location, Timestamp seasonStart, 
			Timestamp seasonEnd) throws RemoteException;
	/**
	 * Deletes a Hotel in the database if the given ID matches any.
	 * @param id The ID of the hotel to be deleted.
	 * @return True if a Hotel has been deleted. False if no Hotel was deleted.
	 * @throws RemoteException Launched by the RMI registry.
	 */
	public boolean deleteHotel(String id) throws RemoteException;
	//TODO getRoomsOfHotelbyID
	//TODO getRoombyID
}
