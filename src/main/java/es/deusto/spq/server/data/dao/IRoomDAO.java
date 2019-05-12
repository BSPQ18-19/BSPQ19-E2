package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import es.deusto.spq.server.data.jdo.Room;

public interface IRoomDAO {

	public ArrayList<Room> getRooms(); 
	public void updateRoom(Room room);
	public boolean deleteRoom(String roomID);
	public List<Room> getRoom(String id);
}
