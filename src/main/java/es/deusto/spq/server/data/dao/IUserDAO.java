package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;

public interface IUserDAO {

	/**
	 * Retrieves all the users in the data base.
	 * @param authorization UserDTO object
	 * @return a list of all the users.
	 */
	public List<UserDTO> getUsers(UserDTO authorization);
	/**
	 * Retrieves the user that matches the ID.
	 * @param authorization - the authorization of the requester.
	 * @param ID the ID of the user to retrieve.
	 * @return the user matching the ID if exists, and null if not.
	 */
	public UserDTO getUserbyID(UserDTO authorization, String ID);
	/**
	 * Stores the new user into the database. Note that User is an abstract class, so the 
	 * argument must an instance of it to store properly. If not, exceptions may arise.
	 * @param user either a Guest or an Administrator, to be stores in the database.
	 * @return a detached copy of the new user, and null if an error has arisen.
	 */
	public UserDTO createUser(User user);
	/**
	 * Deletes the user in the database, and returns a boolean indicating if it has been
	 * or not deleted.
	 * @param authorization - the authorization of the requester.
	 * @param ID the ID of the user to be deleted.
	 * @return true if the user existed before and has been deleted, false if it didn't exist
	 * 			and thus hasn't been deleted.
	 */
	public boolean deleteUserbyID(UserDTO authorization, String ID);
	public UserDTO logIn(String email, String password);
	
	/**
	 * Retrieves all the guests in the data base. In order to call this method, the 
	 * authorization User must be an Administrator.
	 * @param authorization - the authorization of the requester. 
	 * @return a list of all the guests in the data base.
	 */
	public List<Guest> getGuests(UserDTO authorization);

	/**
	 * Retrieves all the administrators in the data base. 
	 * @param authorization - the authorization of the requester. 
	 * @return a list of all the administrators in the data base.
	 */
	public List<Administrator> getAdministrators(UserDTO authorization);
	
	public Guest getGuestByEmail(String email);
	/**
	 * The same as getUserByID but this return the user and not the UserDTO
	 * @param ID
	 * @return User object
	 */
	public User getUser(String ID);
}