package es.deusto.spq;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.UserDAO;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class ReviewDBTest {

	private static Review r;
	private static UserDAO userDAO;
	private static HotelDAO hotelDAO;
	
	private PersistenceManager pm =  MyPersistenceManager.getPersistenceManager();;
	private Transaction tx;
	private Assembler assembler;
	
	@BeforeClass
	public static void setUp() throws Exception {
		User u = new Guest("69", "name", "email", "password", "1", "address");
		userDAO.createUser(u);
		Hotel h =new Hotel("H69", "Hotel1", "Bilbao", Timestamp.valueOf("2019-03-02 00:00:00"), Timestamp.valueOf("2019-03-02 00:00:00"));
		hotelDAO.storeHotel(h);
		r = new Review("1", "Perfect", 9, Timestamp.valueOf("2019-03-02 00:00:00"));
		h.addReview(r);
	}

	@Test
	public void test() {
		
	}
}
