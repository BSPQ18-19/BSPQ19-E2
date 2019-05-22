package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.bloomfilter.SimpleBloomFilter;
import es.deusto.spq.server.data.cache.Cache;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.logger.ServerLogger;

public class RoomDAO implements IRoomDAO {
	
	private PersistenceManager pm;
	private Transaction tx;
	private SimpleBloomFilter<Room> filter;
	/** The cache of the rooms. */
	private Cache<String, Room> cache;

	public RoomDAO(){
		pm = MyPersistenceManager.getPersistenceManager();
		filter = new SimpleBloomFilter<Room>();
		cache = new Cache<String, Room>(10);
	}	

	@Override
	public List<Room> getRooms() {
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
	    List<Room> rooms = new ArrayList<>();
	        
	    try {
	    	ServerLogger.getLogger().info("   * Retrieving all the rooms ");
	    	
	    	tx.begin();	    	
			Extent<Room> extent = pm.getExtent(Room.class, true);

			for (Room room : extent) {
				rooms.add(room);
			}
			
	        tx.commit();
	    } catch (Exception ex) {
	    	ServerLogger.getLogger().fatal("   $ Error retreiving an extent: " + ex.getMessage());
	    } finally {
	    	close();
	    }
	    return rooms;
	}
	
	private Logger log = ServerLogger.getLogger();

	@Override
	public Room updateRoom(Room room) {
		log.debug("A room wants to be updated in RoomDAO - " + room.getRoomId());
		
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
		
		try {
			ServerLogger.getLogger().info("   * Retrieving an Extent for Hotels.");
			
			tx.begin();			
			Query<Room> query = pm.newQuery(Room.class);
			query.setFilter("roomId == '" + room.getRoomId() + "'");
			@SuppressWarnings("unchecked")
			List<Room> result = (List<Room>) query.execute();
			result.get(0).setOccupied(true);
			filter.add(room);
			cache.set(room.getRoomId(), room);
			tx.commit();
			
			return result.get(0);
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	close();
	    }
	    				
		return null;
	}
	
	@Override
	public boolean deleteRoom(String roomID) {
		Room tmpRoom = new Room(roomID, 0, 0, null, true);
		if(!filter.contains(tmpRoom))
			return false;
		cache.remove(roomID);
		
		tx = pm.currentTransaction();
		try {
			tx.begin();
			
			Query<Room> query = pm.newQuery(Room.class);
			query.setFilter("roomId == '" + roomID + "'");
			@SuppressWarnings("unchecked")
			List<Room> queryExecution = (List<Room>) query.execute();
			if(queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));
			tx.commit();
			
			return true;

		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error deleting an hotel: " + ex.getMessage());
	    } finally {
	    	close();
	    }
		return false;
	}

	@Override
	public List<Room> getRoomByHotelId(String hotelId) {
		/* By default only 1 level is retrieved from the db
		 * so if we wish to fetch more than one level, we must indicate it
		 */
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
		
		try {
			ServerLogger.getLogger().info("   * Retrieving an Extent for Rooms.");
			
			tx.begin();			
			Query<Room> query = pm.newQuery(Room.class);
			query.setFilter("hotel.hotelId == '" + hotelId + "'");
			@SuppressWarnings("unchecked")
			List<Room> result = (List<Room>) query.execute();
			tx.commit();
			
			return result;
			
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	close(); 		
	    }
	    				
		return null;
	}

	@Override
	public Room getRoomById(String roomId) {
		Room tmpRoom = new Room(roomId, 0, 0, null, true);
		if(!filter.contains(tmpRoom))
			return null;
		if(cache.contains(roomId))
			return cache.get(roomId);

		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
		
		try {
			ServerLogger.getLogger().info("   * Retrieving an Extent for Rooms.");
			
			tx.begin();			
			Query<Room> query = pm.newQuery(Room.class);
			query.setFilter("roomId == '" + roomId + "'");
			
			@SuppressWarnings("unchecked")
			List<Room> result = (List<Room>) query.execute();
			tx.commit();
			
			return result == null || result.size() == 0 || result.size() > 1 ?
					null :
					result.get(0);
			
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	close();
	    }
	    				
		return null;
	}

	/**
	 * Closes the transaction if it hasn't been closed before, and makes rollback.
	 */
	private final void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}

	@Override
	public Room createRoom(Room room) {
		//Don't create the room if it's already created
		Room existingRoom = getRoomById(room.getRoomId());
		if(existingRoom != null)
			return existingRoom;
		
		try {
			tx = pm.currentTransaction();
			tx.begin();
			pm.makePersistent(room);
			Room result = pm.detachCopy(room);
			filter.add(result);
			cache.set(result.getRoomId(), result);
			tx.commit();
			return result;
		} catch(Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}
		return null;
	}

}
