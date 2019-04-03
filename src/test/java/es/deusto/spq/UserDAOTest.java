package es.deusto.spq;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;

public class UserDAOTest {

	private static UserDAO userDao;
	
	
	@BeforeClass
	public static void initialize() {
		userDao = new UserDAO();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void createAndRetrieveUserbyIDTest() {
		//Create the user
		User user1 = new Guest("4", "name", "email", "password", 1, "address");
		userDao.createUser(user1);
		
		//Retrieve the user
		User user1Test = userDao.getUserbyID("4");
//		Logger.getLogger("log").info(user1Test.toString());
		Assert.assertTrue(user1.equals(user1Test));
		
		//Delete user
		Assert.assertTrue(userDao.deleteUser("4") == 1);
	}

}
