package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import es.deusto.spq.server.data.jdo.Room;

public interface IRoomDAO {

	/** Retrieve all the rooms
	 * @return List of rooms
	 */
	public List<Room> getRooms(); 
	/** Insert/update a room
	 * @param room Room object
	 */
	public void updateRoom(Room room);
	/** Delete a room with the requested room id
	 * @param roomID Id of the room
	 * @return True if it has been deleted
	 */
	public boolean deleteRoom(String roomID);
	/** Retrieve a list of rooms according to a hotel ID
	 * @param hotelId Id of the hotel
	 * @return List of rooms
	 */
	public List<Room> getRoomByHotelId(String hotelId);
	
	/** Get a room according to a room ID
	 * @param roomId Id of the room
	 * @return Room object
	 */
	public Room getRoomById(String roomId);
}
