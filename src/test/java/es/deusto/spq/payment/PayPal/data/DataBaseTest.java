package es.deusto.spq.payment.PayPal.data;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class DataBaseTest {
	
	/** Initializes the database to load data. */
	private static DataBase dataBase;
	/** The user to do transactions. */
	private static User user;
	/** Username of the user. */
	private static String username;
	/** Password of the user. */
	private static String password;
	/** The payment users want to make. */
	private static Payment payment;
	/** The amount of money the payment will cost. */
	private static float paymentAmount;
	
	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		dataBase = DataBase.getDataBase();
		username = "username";
		password = "password";
		paymentAmount = 50.0f;
	}

	/**
	 * Tests whether the registration is successfully done.
	 */
	@Before
	public void registrationTest() {
		User user = new User(username, password);
		Assert.assertTrue(dataBase.registerUser(user));
		payment = new Payment(paymentAmount);
	}
	
	/**
	 * Tests the the payment system to the database.
	 */
	@Test
	public void paymentTest() {
		Assert.assertTrue(dataBase.makePayment(username, password, payment));
	}

}
