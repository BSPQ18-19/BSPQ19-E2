package es.deusto.spq;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import es.deusto.spq.server.data.dao.*;
import es.deusto.spq.server.data.jdo.*;

public class TestDBDAO {
	
	UserDAO dao = new UserDAO();
			
	@Test
	public void test() {
//		try {
//			Guest u = new Guest("0", "Test", "email@email.email", "password", 1111, "Test street");//Test data
//			
//			//Create a user in the Database
//			System.out.println("The user we are creating: " + u);
//			dao.createUser(u);
//			
//			//Get all the users
//			List<User> ul = dao.getUsers();
//			for(User user : ul) {
//				System.out.println("> " + user);
//			}
//			
//			
//			//Get a user using an specific ID
//			assertTrue(u.equals(dao.getUserbyID("0")));
//		}catch (Exception e) {
//			System.out.println("Exception thrown during retrieval of Extent : " + e.getMessage());
//			assertFalse(false);
//		}		
		fail("Not implemented yet");
	}

}
