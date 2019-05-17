package es.deusto.spq.server.data.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.bloomfilter.SimpleBloomFilter;
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
	/**Creates a new instance of the ReservationDAO.
	 * Initializes the persistence manager and the logger, retrieving from the 
	 * managers in the server.
	 */
	public ReservationDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		log = ServerLogger.getLogger();
		filter = new SimpleBloomFilter<Reservation>();
	}

	@Override
	public Reservation getReservationbyID(String ID) {
		Reservation tmpReservation = new Reservation(ID, null, null, null, null);
		if(!filter.contains(tmpReservation))
			return null;
		
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
	public List<Reservation> getReservationsOfGuest(Guest guest) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<Reservation> query = pm.newQuery(Reservation.class);
			query.setFilter("guestId == '" + guest.getUserID() + "'"); //TODO check if this is correct
			@SuppressWarnings("unchecked")
			List<Reservation> result = (List<Reservation>) query.execute();
			tx.commit();
			
			return result == null || result.isEmpty() ?
					null :
					result;
		} catch (Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}
		
		return null;
	}

	@Override
	public synchronized Reservation createReservation(Reservation reservation) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			pm.makePersistent(reservation);
			Reservation detachedCopy = pm.detachCopy(reservation);
			filter.add(detachedCopy);
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
