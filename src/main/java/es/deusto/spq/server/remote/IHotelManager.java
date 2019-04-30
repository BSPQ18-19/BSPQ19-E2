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
	//TODO signInAdministrator
	public UserDTO logIn(String email, String password) throws RemoteException;
	public boolean logOut(UserDTO user) throws RemoteException;
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException;
	public ArrayList<HotelDTO> retrieveHotels(String arrivalDate) throws RemoteException;
	public HotelDTO createHotel(String id, String name, String location, String seasonStart, String seasonEnd) throws RemoteException;
	public boolean deleteHotel(String id) throws RemoteException;
	public boolean cleanDB() throws RemoteException;
	//TODO getRoomsOfHotelbyID
	public ArrayList<RoomDTO> retrieveRooms() throws RemoteException;
	public RoomDTO updateRoom(String roomId, float size, float price, RoomType roomtype, boolean isOccupied) throws RemoteException;
	public boolean deleteRoom(String id) throws RemoteException;
	public ArrayList<RoomDTO> retrieveRoomsById(String hotelID) throws RemoteException;
	
	
	
	
	//TODO getRoombyID
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
