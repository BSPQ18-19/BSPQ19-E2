package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.data.Hotel;

public class HotelDAO implements IHotelDAO {
	
private PersistenceManagerFactory pmf;
private PersistenceManager pm;
private Transaction tx;
	
	public HotelDAO(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	public void storeHotel(Hotel hotel) {
		this.storeObject(hotel);
	}
	
	private void storeObject(Object object) {
		pm = pmf.getPersistenceManager();
	    tx = pm.currentTransaction();
	   
	    try {
	       tx.begin();
	       System.out.println("   * Storing an object: " + object);
	       pm.makePersistent(object);
	       tx.commit();

	    } catch (Exception ex) {
	    	System.out.println("   $ Error storing an object: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}
			if(pm != null && !pm.isClosed()) {
				pm.close();
			}
	    }
	}
	
	public Hotel getHotel(String hotelID) {
		PersistenceManager pm = pmf.getPersistenceManager();
		/* By default only 1 level is retrieved from the db
		 * so if we wish to fetch more than one level, we must indicate it
		 */
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		Transaction tx = pm.currentTransaction();
		
		try {
			System.out.println("   * Retrieving an Extent for Hotels.");
			
			tx.begin();			
			Extent<Hotel> extent = pm.getExtent(Hotel.class, true);
			
			for (Hotel hotel : extent) {
				if (hotel.getName().equals(hotelID)) {
				    return hotel;
                }
			}

			tx.commit();			
		} catch (Exception ex) {
	    	System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		tx.rollback();
	    	}

    		pm.close();    		
	    }
	    				
		return null;
	}


	public ArrayList<Hotel> getHotels() {
		pm = pmf.getPersistenceManager();
//		pm.getFetchPlan().setMaxFetchDepth(3);
		
	    tx = pm.currentTransaction();
	    ArrayList<Hotel> hotels = new ArrayList<>();
	        
	    try {
	    	System.out.println("   * Retrieving all the hotels ");
	    	
	    	tx.begin();	    	
			Extent<Hotel> extent = pm.getExtent(Hotel.class, true);

			for (Hotel hotel : extent) {
				hotels.add(hotel);
			}
			
	        tx.commit();
	    } catch (Exception ex) {
	    	System.out.println("   $ Error retreiving an extent: " + ex.getMessage());
	    } finally {
	    	if (tx != null && tx.isActive()) {
	    		System.out.println("rollback");
	    		tx.rollback();
	    	}
			if(pm != null && !pm.isClosed()) {
				// TODO NULL POINTER EXCEPTION
				System.out.println("  ---> ");
				pm.close();
				System.out.println("  #---> " + hotels.get(0).getLocation());
			}
	    }
    
	    System.out.println(hotels.get(0).getLocation());
	    return hotels;
	}

	public void cleanDB() {
		System.out.println("- Cleaning the DB...");			
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);

		Transaction tx = pm.currentTransaction();
		//Start the transaction
		try {
			tx.begin();

			//Delete hotels from DB
			Query<Hotel> query1 = pm.newQuery(Hotel.class);
			System.out.println(" * '" + query1.deletePersistentAll() + "' hotels deleted from the DB.");

			//End the transaction
			tx.commit();
		} catch (Exception ex) {
			System.err.println(" $ Error cleaning the DB: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
		}
	}	
}
