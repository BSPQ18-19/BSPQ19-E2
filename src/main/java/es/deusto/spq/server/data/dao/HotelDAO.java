package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
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
import es.deusto.spq.server.logger.ServerLogger;

/** DAO class for hotels
 * @author gonzalo
 *
 */
public class HotelDAO implements IHotelDAO {
	
	private PersistenceManager pm;
	private Transaction tx;
	private Logger log;

	public HotelDAO(){
		log = ClientLogger.getLogger();
		pm = MyPersistenceManager.getPersistenceManager();
	}
	
	public void storeHotel(Hotel hotel) {
		this.storeObject(hotel);
	}
	
	/** Store an object into the DB
	 * @param object The object to be stored in the DB
	 */
	private void storeObject(Object object) {
		tx = pm.currentTransaction();
	   
	    try {
	       tx.begin();
	       ServerLogger.getLogger().info("   * Storing an object: " + object);
	       pm.makePersistent(object);
	       tx.commit();

	    } catch (Exception ex) {
	    	ServerLogger.getLogger().fatal("   $ Error storing an object: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
	    }
	}
	
	
	//TODO This method does not work well
	
	public Hotel getHotel(String hotelID) {
		/* By default only 1 level is retrieved from the db
		 * so if we wish to fetch more than one level, we must indicate it
		 */
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
		
		try {
			ServerLogger.getLogger().info("   * Retrieving an Extent for Hotels.");
			
			tx.begin();			
			Extent<Hotel> extent = pm.getExtent(Hotel.class, true);
			tx.commit();
			for (Hotel hotel : extent) {
				if (hotel.getHotelId().equals(hotelID)) {
				    return hotel;
                }
			}
			
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}	
	    }
	    				
		return null;
	}
 

	public ArrayList<Hotel> getHotels() {
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
	    ArrayList<Hotel> hotels = new ArrayList<>();
	        
	    try {
	    	ServerLogger.getLogger().info("   * Retrieving all the hotels ");
	    	
	    	tx.begin();	    	
			Extent<Hotel> extent = pm.getExtent(Hotel.class, true);

			for (Hotel hotel : extent) {
				hotels.add(hotel);
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
	    return hotels;
	}

	public ArrayList<Hotel> getHotels(Timestamp arrivalDate) {
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
	    ArrayList<Hotel> hotels = new ArrayList<>();
	        
	    try {
	    	ServerLogger.getLogger().info("   * Retrieving all the hotels ");
	    	
	    	tx.begin();	    	
			Extent<Hotel> extent = pm.getExtent(Hotel.class, true);

			for (Hotel hotel : extent) {
				if(hotel.getSeasonStart().getTime() <= arrivalDate.getTime())
					hotels.add(hotel);
			}
			
	        tx.commit();
	    } catch (Exception ex) {
	    	ServerLogger.getLogger().fatal("   $ Error retreiving an extent: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		ServerLogger.getLogger().info("rollback");
	    		tx.rollback();
	    	}
//			if(pm != null && !pm.isClosed()) {
//				pm.close();
//			}
	    }
	    return hotels;
	}
	
	@Override
	public boolean deleteHotel(String hotelID) {
		tx = pm.currentTransaction();
		try {
			tx.begin();
			
			Query<Hotel> query = pm.newQuery(Hotel.class);
			query.setFilter("hotelId == '" + hotelID + "'");
			@SuppressWarnings("unchecked")
			List<Hotel> queryExecution = (List<Hotel>) query.execute();
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
	
	public void cleanDB() {
		ServerLogger.getLogger().info("- Cleaning the DB...");
		pm.getFetchPlan().setMaxFetchDepth(3);

		tx = pm.currentTransaction();
		//Start the transaction
		try {
			tx.begin();

			//Delete hotels from DB
			Query<Hotel> query1 = pm.newQuery(Hotel.class);
			ServerLogger.getLogger().info(" * '" + query1.deletePersistentAll() + "' hotels deleted from the DB.");

			//End the transaction
			tx.commit();
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal(" $ Error cleaning the DB: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		}
	}	
}
