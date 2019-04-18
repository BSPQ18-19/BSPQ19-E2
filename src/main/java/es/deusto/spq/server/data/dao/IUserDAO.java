package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.User;

public interface IUserDAO {

	/**
	 * Retrieves all the users in the data base.
	 * @return A list of all the users.
	 */
	public List<User> getUsers();
	/**
	 * Retrieves the user that matches the ID.
	 * @param ID The ID of the user to retrieve.
	 * @return The user matching the ID if exists, and null if not.
	 */
	public User getUserbyID(String ID);
	/**
	 * Creates the user in the data base, and returns a detached copy.
	 * @param user The new user to be stored into the database.
	 * @return A detached copy of the new user, and null if an error has arised.
	 */
	public User createUser(User user);
	/**
	 * Deletes the user in the database, and returns a boolean indicating if it has been
	 * or not deleted.
	 * @param ID the ID of the user to be deleted from the database.
	 * @return True if the ID matched a user and has correctly been deleted. False if it
	 * 			didn't exist or couldn't manage to delete it.
	 */
	public boolean deleteUserbyID(String ID);
	/**
	 * Returns the user that matches the parameters provided.
	 * @param email The email of the user to be logged in.
	 * @param password The password of the user to be logged in.
	 * @return The user that has logged in.
	 */
	public User logIn(String email, String password);
}