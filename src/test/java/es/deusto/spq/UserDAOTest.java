package es.deusto.spq;


import static org.junit.Assert.fail;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.UserDTO;
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
	private static Assembler assembler;
	
	@BeforeClass
	public static void initialize() {
		userDao = new UserDAO();
		assembler = new Assembler();
		userID = "1";
		user = new Guest(userID, "name", "email", "password", "1", "address");
		//TODO initialize user correctly
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void aCreateUser() {
//		User detachedUser = userDao.createUser(user);
//		Assert.assertTrue(detachedUser.equals(user));
		// TODO update this test
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void bGetUserByID() {
//		User detachedUser = userDao.getUserbyID(userID);
//		Assert.assertTrue(detachedUser.equals(user));
		// TODO update this test
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void cGetUsers() {
//		List<User> users = userDao.getUsers();
//		Assert.assertTrue(users.contains(users));
		// TODO update this test
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void dEditUser() {
		String name = "name2", email = "email2", password = "password2", address =  "address2", phone = "phone2";
		UserDTO actualUser = assembler.assembleUser(user);
		//Apply the changes to the actual userDTO to compare it to the one DB gets.
		actualUser.setName(name);
		actualUser.setEmail(email);
		actualUser.setPassword(password);
		actualUser.setAddress(address);
		actualUser.setPhone(phone);
		
		UserDTO changedUser = userDao.editUser(userID, name, email, password, phone, address);
		Assert.assertEquals(actualUser, changedUser);
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void eDeleteUserByID() {
//		Assert.assertTrue(userDao.deleteUserbyID(userID));
		// TODO update this test
	}

}
