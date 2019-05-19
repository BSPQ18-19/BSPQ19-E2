package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.bloomfilter.SimpleBloomFilter;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.Room;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class UserDAO implements IUserDAO {

	private PersistenceManager pm;
	private Transaction tx;
	private Assembler assembler;
	private Logger log;
	private SimpleBloomFilter<User> filter;
	
	public UserDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		assembler = new Assembler();
		log = ServerLogger.getLogger();
		filter = new SimpleBloomFilter<User>();
	}

	@Override
	public List<UserDTO> getUsers() {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			List<UserDTO> result = new ArrayList<UserDTO>();
			for (User user : queryExecution)
				result.add(assembler.assembleUser(user));
			tx.commit();

			return result;

		} catch (Exception e) {
			log.fatal("Error in UserDAO:getUsers()");
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public UserDTO getUserbyID(String ID) {
		//Check if there's a user with such ID
		User tmpUser = new Guest(ID, null);
		if(!filter.contains(tmpUser))
			return null;
		
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			query.setFilter("userID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			List<User> result = (List<User>) query.execute();
			tx.commit();

			return result == null || result.isEmpty() || result.size() > 1 ? 
					null : 
					assembler.assembleUser(result.get(0));
		} catch (Exception e) {
			log.fatal("Error in UserDAO:getUserbyID()");
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public UserDTO createUser(User user) {
		try {
			User result;
			Guest guest;
			Administrator administrator;
			
			tx = pm.currentTransaction();
			tx.begin();

			
			if(user instanceof Guest) {
				guest = (Guest) user;
				pm.makePersistent(guest);
				result = pm.detachCopy(guest);
			} else {
				administrator = (Administrator) user;
				pm.makePersistent(administrator);
				result = pm.detachCopy(administrator);
			}
			//Add the new user to the filter
			filter.add(result);
			
			tx.commit();

			return assembler.assembleUser(result);

		} catch (Exception e) {
			log.fatal("Error in UserDAO:createUser()");
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public boolean deleteUserbyID(String ID) {
		//Check if there's a user with such ID
		User user = new Guest(ID, null);
		if(!filter.contains(user))
			return false;

		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			query.setFilter("userID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			if (queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));

			tx.commit();

			return true;

		} catch (Exception e) {
			log.fatal("Error in UserDAO:deleteUserbyID()");
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	@Override
	public UserDTO logIn(String email, String password) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<Administrator> queryAdmin;
			Query<Guest> queryGuest;
			
			queryGuest = pm.newQuery(Guest.class);
			queryGuest.setFilter("email == '" + email + "'");
			@SuppressWarnings("unchecked")
			List<Guest> resultGuest = (List<Guest>) queryGuest.execute();
			
			if(resultGuest == null || resultGuest.isEmpty()) {
				//No guests found, searching for guests...
				queryAdmin = pm.newQuery(Administrator.class);
				queryAdmin.setFilter("email == '" + email + "'");
				@SuppressWarnings("unchecked")
				List<Administrator> resultAdmin = (List<Administrator>) queryAdmin.execute();
				tx.commit();

				if(resultAdmin == null || resultAdmin.isEmpty())
					log.debug("Neither Guests nor Administrators with such email");
				return assembler.assembleUser(resultAdmin.get(0));
			} else {
				//At least an guest was found
				tx.commit();
				return assembler.assembleUser(resultGuest.get(0));
			}

		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			close();
		}
		return null;
	}
	
	@Override
	public List<Guest> getGuests() {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<Guest> query = pm.newQuery(Guest.class);
			@SuppressWarnings("unchecked")
			List<Guest> result = (List<Guest>) query.execute();
			tx.commit();

			return result;
		} catch (final Exception e) {
			log.fatal("Error while retrieving guests from the database");
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}
	
	@Override
	public List<Administrator> getAdministrators() {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<Administrator> query = pm.newQuery(Administrator.class);
			@SuppressWarnings("unchecked")
			List<Administrator> result = (List<Administrator>) query.execute();
			tx.commit();

			return result;
		} catch (final Exception e) {
			log.fatal("Error while retrieving guests from the database");
			e.printStackTrace();

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
	public Guest getGuestByEmail(String email) {
		pm.getFetchPlan().setMaxFetchDepth(3);
		
		tx = pm.currentTransaction();
		
		try {
			ServerLogger.getLogger().info("   * Retrieving an Extent for Rooms.");
			
			tx.begin();			
			Query<Guest> query = pm.newQuery(Guest.class);
			query.setFilter("email == '" + email + "'");
			
			@SuppressWarnings("unchecked")
			List<Guest> result = (List<Guest>) query.execute();
			tx.commit();
			
			return result.get(0);
			
		} catch (Exception ex) {
			ServerLogger.getLogger().fatal("   $ Error retrieving an extent: " + ex.getMessage());
	    } finally {
	    	close();
	    }
	    				
		return null;
	}

}