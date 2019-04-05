package es.deusto.spq;


import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

/**
 * The test-class for {@link es.deusto.spq.server.data.dao.UserDAO}. 
 * 
 * <p>
 * Methods have a character at the beginning in order to sort them using both {@link org.junit.FixMethodOrder} and
 * {@link org.junit.runners.MethodSorters}.
 * </p>
 * 
 * @author Iker
 *
 */
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
	public void aCreateUser() {
		User detachedUser = userDao.createUser(user);
		Assert.assertTrue(detachedUser.equals(user));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void bGetUserByID() {
		User detachedUser = userDao.getUserbyID(userID);
		Assert.assertTrue(detachedUser.equals(user));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void cGetUsers() {
		List<User> users = userDao.getUsers();
		Assert.assertEquals(users.get(0), user);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void dDeleteUserByID() {
		Assert.assertTrue(userDao.deleteUserbyID(userID));
	}

}
