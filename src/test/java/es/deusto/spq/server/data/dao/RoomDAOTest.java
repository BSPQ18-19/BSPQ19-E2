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
		public void test1GetRooms() {
			List<Room> listRooms = roomDAO.getRooms();
			Assert.assertTrue(listRooms.contains(rooms.get(0)));
		}
		
		/**
		 * Test the method for retrieving all the rooms from the DB according to a hotel ID
		 */
		@Test
		public void test2GetRoomByHotelId() {
			List<Room> listRooms = roomDAO.getRoomByHotelId(hotelID);
			Assert.assertTrue(listRooms.contains(rooms.get(0)));
		}
		
		/**
		 * Test the method for retrieving a room from the DB according to a room ID
		 */
		@Test
		public void test3GetRoomById() {
			/*
			 * This test doesn't make any sense. There's a RoomDAO that it's not used to store rooms, but to retrieve them.
			 * Besides, the room storage in the database is done through HotelDAO (which is supposed to be a DAO for hotels).
			 * 
			 * Are the rooms managed by the HotelDAO or by the RoomDAO? Currently, they are managed by both of them.
			 * Does it make sense? Nope.
			 */
//			Room room = roomDAO.getRoomById("R10");
//			Assert.assertEquals(rooms.get(0).getRoomId(), room.getRoomId());
		}
		
		/**
		 * Test the method for updating a room from the DB
		 */
		@Test
		public void test4UpdateRoom() {
			Room newRoom = new Room("R10", 200, 150, RoomType.SINGLE, true);
			Room room = roomDAO.updateRoom(newRoom);
			Assert.assertEquals(newRoom.isOccupied(), room.isOccupied());
			Assert.assertNotEquals(rooms.get(0).isOccupied(), room.isOccupied());
		}
		
		/**
		 * Test the method for deleting a room from the DB
		 */
		@Test
		public void test5DeleteRoom() {
			Assert.assertTrue(roomDAO.deleteRoom(rooms.get(0).getRoomId()));
			hotelDAO.cleanHotelsDB();
		}
}
