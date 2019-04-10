package es.deusto.spq;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.User;

public class ReviewDBTest {

	private static Review r;
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void setUp() throws Exception {
		r = new Review("1", "Perfect", 9, Timestamp.valueOf("2019-03-02 00:00:00"));
	}

	@Test
	public void test() {
		//userDAO.createReview(r);
	}

}
