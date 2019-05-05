package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.logger.ServerLogger;

public class RoomDAO implements IRoomDAO {
	
	private PersistenceManager pm;
	private Transaction tx;
	private Logger log;

	public RoomDAO(){
		log = ClientLogger.getLogger();
		pm = MyPersistenceManager.getPersistenceManager();
	}	
	
	/** Store an object into the DB
	 * @param object The object to be stored in the DB
	 */
	private void storeObject(Object object) {
		tx = pm.currentTransaction();
	   
	    try {
	       tx.begin();
	       
	       pm.makePersistent(object);
	       tx.commit();

	    } catch (Exception ex) {
	    	ServerLogger.getLogger().fatal("   $ Error inserting/updating an object: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
	    }
	}

	@Override
	public ArrayList<Room> getRooms() {
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
	    ArrayList<Room> rooms = new ArrayList<>();
	        
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
	    	if (tx != null && tx.isActive()) {
	    		ServerLogger.getLogger().info("rollback");
	    		tx.rollback();
	    	}
	    }
	    return rooms;
	}

	@Override
	public void updateRoom(Room room) {
		storeObject(room);
		
	}

	@Override
	public boolean deleteRoom(String roomID) {
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
	    	if (tx != null && tx.isActive()) {
	    		ServerLogger.getLogger().debug("rollback");
	    		tx.rollback();
	    	}
	    }
		return false;
	}

	@Override
	public List<Room> getRoom(String id) {
		/* By default only 1 level is retrieved from the db
		 * so if we wish to fetch more than one level, we must indicate it
		 */
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
		
		try {
			ServerLogger.getLogger().info("   * Retrieving an Extent for Rooms.");
			
			tx.begin();			
			Query<Room> query = pm.newQuery(Room.class);
			query.setFilter("hotel.hotelId == '" + id + "'");
			@SuppressWarnings("unchecked")
			List<Room> result = (List<Room>) query.execute();
			tx.commit();
			
			return result;
			
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}  		
	    }
	    				
		return null;
	}



}
