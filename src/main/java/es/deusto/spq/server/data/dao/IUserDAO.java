package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;

public interface IUserDAO {

	/**
	 * Retrieves all the users in the data base.
	 *
	 * @return a list of all the users.
	 */
	List<UserDTO> getUsers(UserDTO authorization);

	/**
	 * Retrieves the user that matches the ID.
	 *
	 * @param ID the ID of the user to retrieve.
	 * @return the user matching the ID if exists, and null if not.
	 */
	UserDTO getUserbyID(UserDTO authorization, String ID);

	/**
	 * Creates the user in the data base, and returns a detached copy.
	 *
	 * @param user the new user to be stored.
	 * @return a detached copy of the new user, and null if an error has arised.
	 */
	UserDTO createUser(User user);

	/**
	 * Deletes the user in the database, and returns a boolean indicating if it has
	 * been or not deleted.
	 *
	 * @param ID the ID of the user to be deleted.
	 * @return true if the user existed before and has been deleted, false if it
	 *         didn't exist and thus hasn't been deleted.
	 */
	boolean deleteUserbyID(UserDTO authorization, String ID);

	UserDTO logIn(String email, String password);
	
	/**
	 * Updates the data of the guest with the given ID.
	 * @param userId - the ID of the guest to edit data.
	 * @param name - the new name of the guest.
	 * @param email - the new email of the guest.
	 * @param password - the new password of the guest.
	 * @param phone - the new phone of the guest.
	 * @param address - the new address of the guest.
	 * @return {@code true} if data was updated successfully, and 
	 * 			{@code false} if not.
	 */
	public boolean updateGuest(String userId, String name, String email, String password, String phone, String address);
}