package es.deusto.spq.server.data.dao;

import java.util.List;

import javax.jdo.Query;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.logger.ServerLogger;

public class HotelDAO extends DAO implements IHotelDAO {
	
	public HotelDAO() {
		super(ServerLogger.getLogger());
	}

	@Override
	public List<Hotel> getHotels() {
	    try {
			pm.getFetchPlan().setMaxFetchDepth(2);
	    	tx = pm.currentTransaction();
	    	tx.begin();
	    	
	    	Query<Hotel> query = pm.newQuery(Hotel.class);
			@SuppressWarnings("unchecked")
	    	List<Hotel> queryExecution = (List<Hotel>) query.execute();
	        tx.commit();
	        
	        log.info("Hotel full list returned");
	        return queryExecution;
	        
	    } catch (Exception e) {
	    	log.fatal(e.getMessage());
	    } finally {
	    	close();
	    }
	    return null;
	}

	@Override
	public Hotel getHotelbyID(String hotelID) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<Hotel> query = pm.newQuery(Hotel.class);
			query.setFilter("hotelId == '" + hotelID + "'");
			@SuppressWarnings("unchecked")
			List<Hotel> result = (List<Hotel>) query.execute();
			tx.commit();

			return result == null || result.isEmpty() || result.size() > 1 ? 
					null : 
					result.get(0);
		} catch (Exception e) {
			log.fatal(e.getMessage());

		} finally {
			close();
		}

		return null;
	}

	@Override
	public Hotel createHotel(Hotel hotel) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			pm.makePersistent(hotel);
			tx.commit();

			Hotel detachedCopy = pm.detachCopy(hotel);
			log.info("Created Hotel with ID: " + hotel.getHotelId());
			return detachedCopy;

		} catch (Exception e) {
			log.fatal(e.getMessage());

		} finally {
			close();
		}

		return null;
	}

	@Override
	public boolean deleteHotelbyID(String hotelID) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<Hotel> query = pm.newQuery(Hotel.class);
			query.setFilter("hotelId == '" + hotelID + "'");
			@SuppressWarnings("unchecked")
			List<Hotel> queryExecution = (List<Hotel>) query.execute();
			if (queryExecution.isEmpty() || queryExecution.size() > 1) {
				log.warn("Couldn't delete Hotel with ID: " + hotelID + ". Either no existing hotels or more than one match.");
				return false;
			}
			pm.deletePersistent(queryExecution.get(0));

			tx.commit();

			return true;

		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			close();
		}
		return false;
	}
	
}
