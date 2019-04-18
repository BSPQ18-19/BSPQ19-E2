package es.deusto.spq.server.data.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class UserDAO extends DAO implements IUserDAO {

	public UserDAO() {
		super(ServerLogger.getLogger());
	}

	@Override
	public List<User> getUsers() {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			tx.commit();

			log.info("User full list returned");
			return queryExecution;

		} catch (Exception e) {
			log.fatal(e.getMessage());

		} finally {
			close();
		}
		
		return null;
	}

	@Override
	public User getUserbyID(String ID) {
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
			log.fatal(e.getMessage());

		} finally {
			close();
		}

		return null;
	}

	@Override
	public User createUser(User user) {
		try {
			tx = pm.currentTransaction();
			tx.begin();
			pm.makePersistent(user);
			tx.commit();

			User detachedCopy = pm.detachCopy(user);
			log.info("Created User with ID: " + user.getUserID());
			return detachedCopy;

		} catch (Exception e) {
			log.fatal(e.getMessage());

		} finally {
			close();
		}

		return null;
	}

	@Override
	public boolean deleteUserbyID(String ID) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			query.setFilter("userID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			if (queryExecution.isEmpty() || queryExecution.size() > 1) {
				log.warn("Couldn't delete User with ID: " + ID + ". Either no existing users or more than one match.");
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
	
	@Override
	public User logIn(String email, String password) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			query.setFilter("email == '" + email + "'");
			@SuppressWarnings("unchecked")
			List<User> result = (List<User>) query.execute();
			tx.commit();

			if(result == null || result.isEmpty() || result.size() > 1) {
				log.warn("Couldn't log in User with email: " + email);
				return null;
			}
			User user = result.get(0);
			if(user.getPassword().equals(password))
				return user;
		} catch (Exception e) {
			log.fatal(e.getMessage());

		} finally {
			close();
		}
		return null;
	}
	
}