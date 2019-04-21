package es.deusto.spq.payment.PayPal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

public class DataBase {

	private static DataBase dataBase = new DataBase();
	
	private Map<String, User> users;
	private Map<User, List<Payment>> payments;
	private Logger log;
	
	private DataBase() {
		users = new HashMap<String, User>();
		payments = new HashMap<User, List<Payment>>();
		log = PayPalLogger.getLogger();
	}
	
	public static DataBase getDataBase() {
		return dataBase;
	}
	
	public boolean registerUser(User user) {
		if(users.containsKey(user.getUsername())) {
			log.warn("User '" + user.getUsername() + "' is already registered.");
			return false;
		}
		users.put(user.getUsername(), user);
		payments.put(user, null);
		return true;
	}
	
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
		if(payments.get(user) == null)
			payments.put(user, new ArrayList<Payment>());
		
		payments.get(user).add(payment);
		user.pay(payment.getAmount());
		log.info("Payment completed for user '" + username + "'.");
		return true;
	}
	
}
