package es.deusto.spq.server.data.dao;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import junit.framework.Assert;
/**
 * The class that runs test for {@link es.deusto.spq.server.data.dao.UserDAO}.
 * 
 * <p>
 * Methods have a character at the beginning in order to sort them using both
 * {@link org.junit.FixMethodOrder} and {@link org.junit.runners.MethodSorters}.
 * </p>
 * 
 * @author egoes
 *
 */

@SuppressWarnings("deprecation")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {

	//The DAO Class
	private static UserDAO userDAO;

	//The guest user for testing
	private static String guestID;
	private static User guest;
	
	//The admin user for testing
	private static String adminID;
	private static User admin;
	
	//Assembler class and DTO for the authentification method
	private static UserDTO authorization;
	private static UserDTO guestDTO;
	private static UserDTO adminDTO;
	private static Assembler assembler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		assembler = new Assembler();
		userDAO = new UserDAO();

		guestID = "1";
		guest = new Guest(guestID, "TEST", "EMAIL", "TEST", "TEST", "TEST");
		guestDTO = assembler.assembleUser(guest);

		adminID = "2";
		admin = new Administrator(adminID, "TEST", "TEST", "TEST", "TEST");
		adminDTO = assembler.assembleUser(admin);

		authorization = adminDTO;
	}
	/**
	 * Test for storing a user, Guest and Admin.
	 * It checks if the DTO the DB returns is the same as the one we have .
	 * 		done from the original class..
	 */
	@Test
	public void aCreateUser() {
		UserDTO detachedGuest = userDAO.createUser(guest);
		Assert.assertTrue(guestDTO.equals(detachedGuest));
	
		UserDTO detachedAdmin = userDAO.createUser(admin);
		Assert.assertTrue(adminDTO.equals(detachedAdmin));
	}

	/**
	 * Test for returning all the users from the DB.
	 * It checks if the ones created are among the ones we get from the DB.
	 */
	@Test
	public void bGetUsers() {
		List<UserDTO> users = userDAO.getUsers(authorization);
		Assert.assertTrue(users.contains(guestDTO));
		Assert.assertTrue(users.contains(adminDTO));
	}

	/**
	 * Test for getting a user by the ID.
	 * Checks if the userDTO you get and the one you have are the same.
	 */
	@Test
	public void cGetUserByID() {
		UserDTO detachedGuest = userDAO.getUserbyID(authorization, guestID);
		Assert.assertTrue(guestDTO.equals(detachedGuest));

		UserDTO detachedAdmin = userDAO.getUserbyID(authorization, adminID);
		Assert.assertTrue(adminDTO.equals(detachedAdmin));
	}

	/**
	 * Test for log in.
	 * Checks if the DTOit return is the same that you already have.
	 */
	@Test
	public void dLogIn() {
		UserDTO detachedGuest = userDAO.logIn(guest.getEmail(), guest.getPassword());
		Assert.assertTrue(guestDTO.equals(detachedGuest));

		UserDTO detachedAdmin = userDAO.logIn(admin.getEmail(), admin.getPassword());
		Assert.assertTrue(adminDTO.equals(detachedAdmin));
	}

	/**
	 * Test of get guest.
	 * See if the guest is among the ones returned by the DB 
	 * 		and if the admin is not one of them.
	 */
	@Test
	public void eGetGuests() {
		List<Guest> guests = userDAO.getGuests(authorization);
		Assert.assertTrue(guests.contains(guest));
		Assert.assertFalse(guests.contains(admin));
	}

	/**
	 * Test of get administrators.
	 * See if the admin is among the ones returned by the DB 
	 * 		and if the guest is not one of them.
	 */
	@Test
	public void fGetAdministrators() {
		List<Administrator> administrators = userDAO.getAdministrators(authorization);
		Assert.assertTrue(administrators.contains(admin));
		Assert.assertFalse(administrators.contains(guest));
	}

	/**
	 * Test the delete user method.
	 * If deleting returns true it means it has been successfully deleted.
	 */
	@Test
	public void gDeleteUser() {
		Assert.assertTrue(userDAO.deleteUserbyID(authorization, guestID));
		Assert.assertTrue(userDAO.deleteUserbyID(authorization, adminID));
	}
}
