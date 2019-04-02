package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.jdo.User;

public interface IUserDAO {

	public List<User> getUsers();
	public User getUserbyID(String ID);
	public User createUser(User user);
	
}
