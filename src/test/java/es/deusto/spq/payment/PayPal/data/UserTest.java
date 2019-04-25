package es.deusto.spq.payment.PayPal.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {

	/** An instance of the class to be tested. */
	private static User user;
	/** The username of the user. */
	private static String username;
	/** The password of the user. */
	private static String password;
	/** The amount of money of the user. */
	private static float amount;
	
	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		username = "username";
		password = "password";
		amount = 3.54f;
	}

	/**
	 * Sets up the data to run properly all the tests.
	 */
	@Before
	public void setUp() {
		user = new User(username, password, amount);
	}
	
	/**
	 * Tests the {@code pay} method of the User.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void payTest() {
		float paymentQuantity = 2.5f;
		float before = user.getMoney();
		user.pay(paymentQuantity);
		Assert.assertEquals(before - paymentQuantity, user.getMoney(), 0.001);
		before = user.getMoney();
		user.pay(paymentQuantity);
		Assert.assertEquals(before - paymentQuantity, user.getMoney(), 0.001);
	}
	
	/**
	 * Tests the username in the User. Note that User has no setter for the username.
	 */
	@Test
	public void usernameTest() {
		Assert.assertEquals(username, user.getUsername());
	}
	
	/**
	 * Tests the password in the user. Note that User has no setter for the password.
	 */
	@Test
	public void passwordTest() {
		Assert.assertEquals(password, user.getPassword());
	}
	
	/**
	 * Tests the money the user has. Note that User has no setter for the money.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void moneyTest() {
		Assert.assertEquals(amount, user.getMoney(), 0.001);
	}

}
