package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.dto.UserDTO;

public interface IUserDAO {

	/**
	 * Retrieves all the users in the data base.
	 * @return a list of all the users.
	 */
	public List<UserDTO> getUsers(UserDTO authorization);
	/**
	 * Retrieves the user that matches the ID.
	 * @param ID the ID of the user to retrieve.
	 * @return the user matching the ID if exists, and null if not.
	 */
	public UserDTO getUserbyID(UserDTO authorization, String ID);
	/**
	 * Creates the user in the data base, and returns a detached copy.
	 * @param user the new user to be stored.
	 * @return a detached copy of the new user, and null if an error has arised.
	 */
	public UserDTO createUser(UserDTO user);
	/**
	 * Deletes the user in the database, and returns a boolean indicating if it has been
	 * or not deleted.
	 * @param ID the ID of the user to be deleted.
	 * @return true if the user existed before and has been deleted, false if it didn't exist
	 * 			and thus hasn't been deleted.
	 */
	public boolean deleteUserbyID(UserDTO authorization, String ID);
}