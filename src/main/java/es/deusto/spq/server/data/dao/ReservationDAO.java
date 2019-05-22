package es.deusto.spq.server.data.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.data.jdo.Hotel;
import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.bloomfilter.SimpleBloomFilter;
import es.deusto.spq.server.data.cache.Cache;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Reservation;
import es.deusto.spq.server.logger.ServerLogger;

public class ReservationDAO implements IReservationDAO {

	/** The persistence manager to deal with objects. */
	private PersistenceManager pm;
	/** The transactions to be made. */
	private Transaction tx;
	/** The log to log to. */
	private Logger log;
	/** The Bloom filter. */
	private SimpleBloomFilter<Reservation> filter;
	/** The cache of reservations. */
	private Cache<String, Reservation> cache;
	
	/**Creates a new instance of the ReservationDAO.
	 * Initializes the persistence manager and the logger, retrieving from the 
	 * managers in the server.
	 */
	public ReservationDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		log = ServerLogger.getLogger();
		filter = new SimpleBloomFilter<Reservation>();
		cache = new Cache<String, Reservation>(10);
	}

	@Override
	public Reservation getReservationbyID(String ID) {
		Reservation tmpReservation = new Reservation(ID, null, null, null, null);
		if(!filter.contains(tmpReservation))
			return null;
		if(cache.contains(ID))
			return cache.get(ID);
		
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<Reservation> query = pm.newQuery(Reservation.class);
			query.setFilter("reservationID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			List<Reservation> result = (List<Reservation>) query.execute();
			tx.commit();
			
			return result == null || result.isEmpty() || result.size() > 1 ?
					null :
					result.get(0);
		} catch (Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}
		
		return null;
	}

	@Override
	public List<Reservation> getReservationsOfGuest(String guestID) {
		pm.getFetchPlan().setMaxFetchDepth(3);

		tx = pm.currentTransaction();
		ArrayList<Reservation> reservations = new ArrayList<>();

		try {
			ServerLogger.getLogger().info("   * Retrieving all the hotels ");

			tx.begin();
			Extent<Reservation> extent = pm.getExtent(Reservation.class, true);

			for (Reservation reservation : extent) {
				if(reservation.getGuestId().equals(guestID))
					reservations.add(reservation);
			}

			tx.commit();
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			close();
		}
		return reservations;
	}

	@Override
	public synchronized Reservation createReservation(Reservation reservation) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			pm.makePersistent(reservation);
			Reservation detachedCopy = pm.detachCopy(reservation);
			filter.add(detachedCopy);
			cache.set(reservation.getReservationID(), reservation);
			tx.commit();
			
			log.info("Created reservation with ID: " + reservation.getReservationID());
			return detachedCopy;
			
		} catch (Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}
		log.warn("Did not create reservation with ID: " + reservation.getReservationID());
		return null;
	}
	
	@Override
	public synchronized boolean deleteReservationByID(String ID) {
		Reservation tmpReservation = new Reservation(ID, null, null, null, null);
		if(!filter.contains(tmpReservation))
			return false;
		cache.remove(ID);

		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<Reservation> query = pm.newQuery(Reservation.class);
			query.setFilter("reservationID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			List<Reservation> queryExecution = (List<Reservation>) query.execute();
			if (queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));

			tx.commit();
			log.info("Deleted reservation with ID: " + ID);
			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}
		log.warn("Did not delete reservation with ID: " + ID);
		return false;
	}
	
	/**Closes the transaction if it's still active.
	 * Note that if no commit has been done, a rollback is performed.
	 */
	private final void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}

}
