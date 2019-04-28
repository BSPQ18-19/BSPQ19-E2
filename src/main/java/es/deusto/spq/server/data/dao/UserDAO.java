package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Administrator;
import es.deusto.spq.server.data.jdo.Guest;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class UserDAO implements IDAO, IUserDAO {

	private final PersistenceManager pm;
	private Transaction tx;
	private final Assembler assembler;

	public UserDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		assembler = new Assembler();
	}

	@Override
	public boolean checkAuthorizationIsAdmin(UserDTO authorization) {
		// TODO
		return true;
	}

	@Override
	public List<UserDTO> getUsers(UserDTO authorization) {
		if (!checkAuthorizationIsAdmin(authorization))
			return null;

		try {
			tx = pm.currentTransaction();
			tx.begin();

			final Query<User> query = pm.newQuery(User.class);
			@SuppressWarnings("unchecked")
			final
			List<User> queryExecution = (List<User>) query.execute();
			final List<UserDTO> result = new ArrayList<>();
			for (final User user : queryExecution)
				result.add(assembler.assembleUser(user));
			tx.commit();

			return result;

		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:getUsers()");
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public UserDTO getUserbyID(UserDTO authorization, String ID) {
		if (!checkAuthorizationIsAdmin(authorization))
			return null;
		try {
			tx = pm.currentTransaction();
			tx.begin();

			final Query<User> query = pm.newQuery(User.class);
			query.setFilter("userID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			final
			List<User> result = (List<User>) query.execute();
			tx.commit();

			return result == null || result.isEmpty() || result.size() > 1 ?
					null :
					assembler.assembleUser(result.get(0));
		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:getUserbyID()");
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

			tx.commit();

			return assembler.assembleUser(result);

		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:createUser()");
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public boolean deleteUserbyID(UserDTO authorization, String ID) {
		if (!checkAuthorizationIsAdmin(authorization))
			return false;
		try {
			tx = pm.currentTransaction();
			tx.begin();

			final Query<User> query = pm.newQuery(User.class);
			query.setFilter("userID == '" + ID + "'");
			@SuppressWarnings("unchecked")
			final
			List<User> queryExecution = (List<User>) query.execute();
			if (queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));

			tx.commit();

			return true;

		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:deleteUserbyID()");
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

			final Query<User> query = pm.newQuery(User.class);
			query.setFilter("email == '" + email + "'");
			@SuppressWarnings("unchecked")
			final
			List<User> result = (List<User>) query.execute();
			tx.commit();

			if(result == null || result.isEmpty() || result.size() > 1)
				return null;
			final User user = result.get(0);
			if(user.getPassword().equals(password))
				return assembler.assembleUser(user);
		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:getUserbyID()");
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}

	@Override
	public List<Guest> getGuests(UserDTO authorization) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			final Query<Guest> query = pm.newQuery(Guest.class);
			@SuppressWarnings("unchecked")
			final
			List<Guest> result = (List<Guest>) query.execute();
			tx.commit();

			return result;
		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error while retrieving guests from the database");
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}

	@Override
	public List<Administrator> getAdministrators(UserDTO authorization) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			final Query<Administrator> query = pm.newQuery(Administrator.class);
			@SuppressWarnings("unchecked")
			final
			List<Administrator> result = (List<Administrator>) query.execute();
			tx.commit();

			return result;
		} catch (final Exception e) {
			ServerLogger.getLogger().fatal("Error while retrieving guests from the database");
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
}