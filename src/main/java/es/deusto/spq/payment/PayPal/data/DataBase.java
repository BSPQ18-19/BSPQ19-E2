package es.deusto.spq.payment.PayPal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

/**
 * This class is a simplification of a DAO object and a real database. For simplification
 * purposes, no database has been created in this server. The amount of connections and users
 * the server will ever have will be small, so there won't be any problem storing the
 * data this class will store in memory. However, if this code is going to be used in a
 * scalable project, the approach mentioned at the beginning of the paragraph is recommended
 * to be followed.
 * 
 * @author Iker
 *
 */
public class DataBase {

	/** The only instance of the DataBase. */
	private static DataBase dataBase = new DataBase(); //Singleton instance
	
	/** A mapping between the username and a user account. */
	private Map<String, User> users;
	/** A mapping between a user account and the list of payments it has done. */
	private Map<User, List<Payment>> payments;
	/** The logger to log to. */
	private Logger log;
	
	/**
	 * Creates a new instance of the DataBase. It initializes all the data structures needed.
	 */
	private DataBase() {
		users = new HashMap<String, User>();
		payments = new HashMap<User, List<Payment>>();
		log = PayPalLogger.getLogger();
	}
	
	/**
	 * Returns the database instance, since no more instance creations are allowed.
	 * @return the only database of the PayPal server.
	 */
	public static DataBase getDataBase() {
		return dataBase;
	}
	
	/**
	 * Registers the user to the database.
	 * @param user - the user to be registered into the database.
	 * @return <code>true</code> if the user is correctly created.
	 * 			<code>false</code> if the user was created before, and no new user is created.
	 */
	public boolean registerUser(User user) {
		if(users.containsKey(user.getUsername())) {
			log.warn("User '" + user.getUsername() + "' is already registered.");
			return false;
		}
		users.put(user.getUsername(), user);
		payments.put(user, null);
		return true;
	}
	
	/**
	 * Makes the given payment to the user with matching with the given username and
	 * password.
	 * @param username - the username of the user who is going to make the payment.
	 * @param password - the password of the user who is going to make the payment.
	 * @param payment - the payment the user is going to do.
	 * @return <code>true</code> if the payment is successfully completed.
	 * 			<code>false</code> if the user isn't registered or the password is wrong.
	 */
	public boolean makePayment(String username, String password, Payment payment) {
		if(!users.containsKey(username)) {//User not registered
			log.warn("User '" + username + "' is not registered.");
			return false;
		}
		
		User user = users.get(username);
		if(!user.getPassword().equals(password)) { //Wrong password
			log.warn("Did not complete payment for '" + username + "', wrong password.");
			return false;
		}
		
		//Initialize List of payments in case it wasn't initialized before
		if(payments.get(user) == null)
			payments.put(user, new ArrayList<Payment>());
		
		payments.get(user).add(payment);
		user.pay(payment.getAmount());
		log.info("Payment completed for user '" + username + "'.");
		return true;
	}
	
}
