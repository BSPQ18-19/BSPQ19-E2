package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.User;

public interface IUserDAO {

	/**Retrieves all the users in the data base.
	 * @return a list of all the users.
	 */
	public List<User> getUsers();
	/**Retrieves the user that matches the ID.
	 * @param ID the ID of the user to retrieve.
	 * @return the user matching the ID if exists, and null if not.
	 */
	public User getUserbyID(String ID);
	/**Creates the user in the data base, and returns a detached copy.
	 * @param user the new user to be stored.
	 * @return a detached copy of the new user, and null if an error has arised.
	 */
	public User createUser(User user);
	
}
