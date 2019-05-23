package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.bloomfilter.SimpleBloomFilter;
import es.deusto.spq.server.data.cache.Cache;
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
	/** The cache of users. */
	private Cache<String, User> cache;//id:String -> user:User
	
	public UserDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		assembler = new Assembler();
		log = ServerLogger.getLogger();
		filter = new SimpleBloomFilter<User>();
		cache = new Cache<String, User>(10);
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
		
		//Check if the user is in the cache
		User userCache = cache.get(ID);
		if(userCache != null)
			return assembler.assembleUser(userCache);
		
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
	public User getUser(String ID) {
		
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
					result.get(0);
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
		//Don't create a user if it already exists
		UserDTO existingUser = getUserbyID(user.getUserID());
		log.debug("existing user: " + existingUser != null);
		if(existingUser != null)
			return existingUser;
		
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
			//Add the user to the cache
			cache.set(result.getUserID(), result);
			
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
		
		cache.remove(ID);

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
				cache.get(resultAdmin.get(0).getUserID());
				return assembler.assembleUser(resultAdmin.get(0));
			} else {
				//At least an guest was found
				tx.commit();
				cache.get(resultGuest.get(0).getUserID());
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
	public UserDTO updateGuest(String userId, String name, String email, String password, String phone, String address) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			final Query<Guest> query = pm.newQuery(Guest.class);
			query.setFilter("userID == '" + userId + "'");
			@SuppressWarnings("unchecked")
			final
			List<Guest> result = (List<Guest>) query.execute();

			if(result == null || result.isEmpty() || result.size() > 1)
				return null;
			Guest guest = result.get(0);
			
			pm.makePersistent(guest);
			if(name != null && !name.isEmpty())
				guest.setName(name);
			if(email != null && !name.isEmpty())
				guest.setEmail(email);
			if(password != null && !password.isEmpty())
				guest.setPassword(password);
			if(phone != null && !phone.isEmpty())
				guest.setPhone(phone);
			if(address != null && !address.isEmpty())
				guest.setAddress(address);
			
			tx.commit();
			UserDTO editedUser = assembler.assembleUser(pm.detachCopy(guest));
			return editedUser;
			
		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Did not update data of guest with ID: " + userId);
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