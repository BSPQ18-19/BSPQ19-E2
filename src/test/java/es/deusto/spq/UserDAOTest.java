package es.deusto.spq;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {

	private static UserDAO userDao;
	private static User user;
	private static String userID;
	
	@BeforeClass
	public static void initialize() {
		userDao = new UserDAO();
		userID = "1";
		user = new Guest(userID, "name", "email", "password", 1, "address");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void firstCreateUser() {
		User detachedUser = userDao.createUser(user);
		Assert.assertTrue(detachedUser.equals(user));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void secondGetUserByID() {
		User detachedUser = userDao.getUserbyID(userID);
		Assert.assertTrue(detachedUser.equals(user));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void thirdGetUsers() {
		List<User> users = userDao.getUsers();
		Assert.assertEquals(users.size(), 1);
		Assert.assertEquals(users.get(0), user);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void fourthDeleteUserByID() {
		Assert.assertTrue(userDao.deleteUserbyID(userID));
	}

}
