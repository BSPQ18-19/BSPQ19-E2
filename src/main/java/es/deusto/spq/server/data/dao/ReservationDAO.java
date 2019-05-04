package es.deusto.spq.server.data.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Reservation;
import es.deusto.spq.server.logger.ServerLogger;

public class ReservationDAO implements IReservationDAO {

	private PersistenceManager pm;
	private Transaction tx;
	private Logger log;
	
	public ReservationDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		log = ServerLogger.getLogger();
	}

	@Override
	public Reservation getReservationbyID(String ID) {
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
			query.setFilter("guest.userID == '" + guest.getUserID() + "'"); //TODO check if this is correct
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
	public Reservation createReservation(Reservation reservation) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			pm.makePersistent(reservation);
			Reservation detachedCopy = pm.detachCopy(reservation);
			tx.commit();
			
			return detachedCopy;
			
		} catch (Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}

		return null;
	}
	
	@Override
	public boolean deleteReservationByID(String ID) {
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

			return true;

		} catch (Exception e) {
			log.warn(e.getMessage());
		} finally {
			close();
		}
		return false;
	}
	
	private final void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}

}
