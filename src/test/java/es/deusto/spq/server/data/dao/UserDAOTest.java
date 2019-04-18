package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

/**
 * The test-class for {@link es.deusto.spq.server.data.dao.UserDAO}.
 * 
 * @author Iker
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {

	/** The mock object to be tested */
	private UserDAO userDao = Mockito.mock(UserDAO.class);
	/** The list of users simulating the database */
	private List<User> users = new ArrayList<User>();
	/** The list of IDs of the users */
	private List<String> userIDs = new ArrayList<String>();
	/** The number of users to be generated. Must be, at least, 1. */
	private int sampleSize = 10;
	/** The prefix of the IDs to be generated. Pattern: ID+number */
	private String idPrefix = "U";
	/**
	 * The first user created and located in the first place of <code>users</code> list.
	 * Retrieving through the expression <code>users.get(0)</code> should return the same.
	 */
	private User firstUser = null;
	/** The ID of the user placed in <code>firstUser</code> */
	private String firstUserID = null;
	/** The email of the user placed in <code>firstUser</code> */
	private String testMail = null;
	/** The password of the user placed in <code>firstUser</code> */
	private String testPassword = null;
	
	/**
	 * Initializes all necessary variables to support the unit tests.
	 */
	@Before
	public void setUp() {
		String currentID;
		for (int i = 0; i < sampleSize; i++) {
			currentID = idPrefix + i;
			userIDs.add(currentID);
			users.add(new Guest(currentID, "name" + currentID));
		}
		firstUser = users.get(0);
		firstUserID = firstUser.getUserID();
		firstUser.setEmail(testMail);
		firstUser.setPassword(testPassword);
	}
	
	/**
	 * Clears all the data, both the one initialized before the test executes and the test-case
	 * has manipulated. 
	 */
	@After
	public void tearDown() {
		userIDs.clear();
		users.clear();
		firstUser = null;
		firstUserID = null;
		testMail = null;
		testPassword = null;
	}
	
	/**
	 * Tests whether it returns all users.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void getUsersTest() {
		Mockito.doReturn(users).when(userDao).getUsers();
		Assert.assertEquals(users, userDao.getUsers());
	}

	/**
	 * Tests whether it finds appropriately the correct user.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void getUsersbyIDTest() {
		Mockito.doReturn(firstUser).when(userDao).getUserbyID(firstUserID);
		Assert.assertEquals(firstUser, userDao.getUserbyID(firstUserID));
	}
	
	/**
	 * Tests whether the user creation is performed correctly.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void createUserTest() {
		String testID = "test01";
		User testUser = new Guest(testID, "testName");
		Mockito.doReturn(testUser).when(userDao).createUser(testUser);
		Assert.assertEquals(testUser, userDao.createUser(testUser));
	}
	
	/**
	 * Tests whether the user deletion is performed correctly.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void deleteUserbyIDTest() {
		Mockito.doReturn(users.remove(firstUser)).when(userDao).deleteUserbyID(firstUserID);
		Assert.assertEquals(true, userDao.deleteUserbyID(firstUserID));
		Assert.assertEquals(false, users.contains(firstUser));

		//Update the value to be returned
		Mockito.doReturn(users.remove(firstUser)).when(userDao).deleteUserbyID(firstUserID);
		Assert.assertEquals(false, userDao.deleteUserbyID(firstUserID));
	}
	
	/**
	 * Tests whether the log in is succesfully coded.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void logInTest() {
		Mockito.doReturn(firstUser).when(userDao).logIn(testMail, testPassword);
		Assert.assertEquals(firstUser, userDao.logIn(testMail, testPassword));
	}
	
}
