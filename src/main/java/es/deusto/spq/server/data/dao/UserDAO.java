package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.jdo.User;

public class UserDAO implements IUserDAO {

	private PersistenceManager pm;
	private Transaction tx;
	
	public UserDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
	}
	
	@Override
	public List<User> getUsers(){
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<User> query = pm.newQuery(User.class);
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			List<User> result = new ArrayList<User>();
			for(User user : queryExecution)
				result.add(pm.detachCopy(user));
			tx.commit();
			
			return queryExecution;
			
		} catch (Exception e) {
			System.err.println("Error in UserDAO:getUsers()"); //TODO replace sysos with logger
			e.printStackTrace();
			
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
					(User) pm.detachCopy(result.get(0));
		} catch (Exception e) {
			System.err.println("Error in UserDAO:getUserbyID()"); //TODO replace sysos with logger
			e.printStackTrace();
			
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
			
			return pm.detachCopy(user);
			
		} catch (Exception e) {
			System.err.println("Error in UserDAO:createUser()"); //TODO replace with logger
			e.printStackTrace();
			
		} finally {
			close();
		}
		
		return null;
	}
	
	private final void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}

	@Override
	public boolean deleteUser(User user) {
		return deleteUserbyID(user.getUserID());
	}
	
	@Override
	public boolean deleteUserbyID(String ID) { //TODO
		try {
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<User> query = pm.newQuery(User.class);
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			if(queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));
			
			tx.commit();
			
			return true;

		} catch (Exception e) {
			
		} finally {
			close();
		}
		return false;
	}
	
}
