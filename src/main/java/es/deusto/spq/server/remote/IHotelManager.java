package es.deusto.spq.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.RoomDTO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.RoomType;

public interface IHotelManager extends Remote {

	public UserDTO signInGuest(String name, String email, String password, String phone, String address) throws RemoteException;
	/**
	 * The method that creates the admin
	 * @param name
	 * @param email
	 * @param password
	 * @param phone
	 * @param address
	 * @return The UserDTO of the created user
	 * @throws RemoteException
	 */
	public UserDTO signInAdmin(String name, String email, String password, String address) throws RemoteException;
	public UserDTO logIn(String email, String password) throws RemoteException;
	public boolean logOut(UserDTO user) throws RemoteException;
	
	/** Retrieve a list of hotels from the DB
	 * @return A list of HotelDTO of type ArrayList
	 * @throws RemoteException
	 */
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException;
	
	/** Retrieve a list of hotels from the DB accordint to an arrival date
	 * @param arrivalDate Date to arrive to the hotel
	 * @return A list of HotelDTO of type ArrayList
	 * @throws RemoteException
	 */
	public ArrayList<HotelDTO> retrieveHotels(String arrivalDate) throws RemoteException;
	
	/** Create a new Hotel of type HotelDTO
	 * @param id Id of the hotel
	 * @param name Name of the hotel
	 * @param location Location of the hotel
	 * @param seasonStart Date when the hotel starts being available
	 * @param seasonEnd Date when the hotel ends being available
	 * @return An object of type HotelDTO
	 * @throws RemoteException
	 */
	public HotelDTO createHotel(String id, String name, String location, String seasonStart, String seasonEnd) throws RemoteException;
	
	/** Delete a hotel from the DB according to an id
	 * @param id Id of the hotel to be deleted
	 * @return True if the hotel has been deleted successfully
	 * @throws RemoteException
	 */
	public boolean deleteHotel(String id) throws RemoteException;
	
	/** Remove all the hotels from the DB
	 * @return True if the DB has cleaned successfully
	 * @throws RemoteException
	 */
	public boolean cleanHotelsDB() throws RemoteException;
	
	/** Retrieve a list of rooms from the DB
	 * @return List of rooms of type ArrayList<RoomDTO>
	 * @throws RemoteException
	 */
	public ArrayList<RoomDTO> retrieveRooms() throws RemoteException;
	
	/** Insert/Update a room in the DB
	 * @param roomId Id of the room
	 * @param size Size of the room
	 * @param price Price of the room
	 * @param roomtype Type of the room
	 * @param isOccupied True if the room is occupied
	 * @return An object of type RoomDTO
	 * @throws RemoteException
	 */
	public RoomDTO updateRoom(String roomId, float size, float price, RoomType roomtype, boolean isOccupied) throws RemoteException;
	
	/** Delete a room form the DB according to an id
	 * @param id Id of the room
	 * @return True if the room has been deleted successfully
	 * @throws RemoteException
	 */
	public boolean deleteRoom(String id) throws RemoteException;
	
	/** Retrieve a list of rooms from the DB according to a hotelId
	 * @param hotelID Id of the hotel
	 * @return List of rooms of type ArrayList<RoomDTO>
	 * @throws RemoteException
	 */
	public ArrayList<RoomDTO> retrieveRoomsById(String hotelID) throws RemoteException;
	
	/**
	 * Registers the user to the PayPal server.
	 * @param username - the username of the new account.
	 * @param password - the password of the new account.
	 * @return {@code true} if the the user has been successfully registered, and 
	 * 			{@code false} if not.
	 */
	public boolean registerPayPal(String username, String password) throws RemoteException;
	/**
	 * Registers the user to the PayPal server.
	 * @param username - the username of the new account.
	 * @param password - the password of the new account.
	 * @param quantity - the amount of money the account will have.
	 * @return {@code true} if the the user has been successfully registered, and 
	 * 			{@code false} if not.
	 */
	public boolean registerPayPal(String username, String password, float quantity) throws RemoteException;
	/**
	 * Makes a payment of the given quantity to the PayPal account.
	 * @param username - the username of the account to make the payment.
	 * @param password - the password of the account to make the payment.
	 * @param quantity - the quantity to be paid.
	 * @return {@code true} if the payment has been done successfully and 
	 * 			{@code false} if not.
	 */
	public boolean payPayPal(String username, String password, float quantity) throws RemoteException;
	/**
	 * Makes a payment of the given quantity to the credit card.
	 * @param cardNumber - the card number to make the payment.
	 * @param securityCode - the security code to make the payment.
	 * @param amount - the amount of money to be paid.
	 * @return {@code true} if the payment has been done successfully, and 
	 * 		{@code false} if not.
	 */
	public boolean payMastercard(long cardNumber, int securityCode, float amount) throws RemoteException;
}
