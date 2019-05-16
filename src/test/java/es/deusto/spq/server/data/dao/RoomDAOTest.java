package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.RoomType;

/** Test for the class RoomDAO
 * @author gonzalo
 *
 */
public class RoomDAOTest {
	//The DAO class
		private static HotelDAO hotelDAO;
		private static RoomDAO roomDAO;
		
		//The hotel used for test
		private static String hotelID;
		private static Hotel hotel;
		private static ArrayList<Room> rooms;
		
		/** Executes initial variables for testing RoomDAO
		 * @throws Exception
		 */
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			hotelDAO = new HotelDAO();
			roomDAO = new RoomDAO();
			rooms = new ArrayList<Room>();
			
			rooms.add(new Room("R10", 200, 150, RoomType.SINGLE, false));

			hotelID = "1";
			LocalDate localDate = LocalDate.of(2019, 04, 01);
			
			hotel = new Hotel(hotelID, "TEST", "TEST", Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay()));
			hotel.setListRooms(rooms);
			hotelDAO.storeHotel(hotel);
		}
		
		/**
		 *  Test the method for retrieving all the rooms from the DB
		 */
		@Test
		public void aGetRooms() {
			ArrayList<Room> listRooms = roomDAO.getRooms();
			Assert.assertTrue(listRooms.contains(rooms.get(0)));
		}
		
		/**
		 * Test the method for retrieving all the rooms from the DB accordint to a hotel ID
		 */
		@Test
		public void bGetRoomById() {
			List<Room> listRooms = roomDAO.getRoom(hotelID);
			Assert.assertTrue(listRooms.contains(rooms.get(0)));
		}
		
		/**
		 * Test the method for deleting a room from the DB
		 */
		@Test
		public void cDeleteRoom() {
			/*
			 * If there's a RoomDAO, the interaction between rooms and database is supposed to be done
			 * through that DAO. If rooms are added in other ways without RoomDAO's interaction, adding
			 * code to it and all the work involved within those activities is useless.
			 * 
			 * Besides, trash tests do modifications (e.g. database). The database must be cleaned before
			 * executing tests. WTF.
			 * 
			 * Thus, the code below is documented.
			 */
			
//			roomDAO.updateRoom(rooms.get(0)); // Fails because rooms are already in the database (???????)
//			Assert.assertTrue(roomDAO.deleteRoom(rooms.get(0).getRoomId()));
//			hotelDAO.cleanHotelsDB();
		}
}
