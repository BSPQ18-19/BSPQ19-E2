package es.deusto.spq.server.data.dao;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;

public abstract class DAO {

	protected PersistenceManager pm;
	protected Transaction tx;
	protected Logger log;
	
	public DAO(Logger log) {
		this.log = log;
		pm = MyPersistenceManager.getPersistenceManager();
	}
	
	
	/**
	 * Removes all the elements in the database related to the given class.
	 * @param persistentClass The class to be removed from the database.
	 */
	public void cleanDataBase(Class<?> persistentClass) {
		try {
			tx.begin();
			@SuppressWarnings("unchecked")
			Query<Object> query = (Query<Object>) pm.newQuery(persistentClass);
			pm.deletePersistent(query);
			log.warn("Deleted all " + persistentClass.getName() + " in the database");
			tx.commit();
		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			close();
		}
	}
	
	/**
	 * Closes the transaction if it hasn't been closed before, and makes rollback.
	 */
	protected final void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}

}
