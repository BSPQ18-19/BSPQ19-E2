package es.deusto.spq.server.gateway;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class PayPalGatewayTest {

	/** The gateway to be tested. */
	private static PayPalGateway payPalGateway;
	/** The username of the user to be tested. */
	private static String username;
	/** The password of the user to be tested. */
	private static String password;
	/** The quantity of money to do tests. */
	private static float quantity;
	
	/**
	 * Initializes all the data needed to perform tests.
	 */
	@BeforeClass
	public static void initialize() {
		payPalGateway = Mockito.spy(new PayPalGateway());
		username = "username";
		password = "password";
		quantity = 5.0f;
	}
	
	/**
	 * Tests the registration account without the quantity parameter.
	 */
	@Test
	public void registerAccountTest() {
		Mockito.doReturn(true).when(payPalGateway).registerAccount(username, password);
		Assert.assertTrue(payPalGateway.registerAccount(username, password));
		String newPassword = password + "test";
		Mockito.doReturn(false).when(payPalGateway).registerAccount(username, newPassword);
		Assert.assertFalse(payPalGateway.registerAccount(username, newPassword));
		String newUsername = username + "test";
		Mockito.doReturn(true).when(payPalGateway).registerAccount(newUsername, newPassword);
		payPalGateway.registerAccount(newUsername, newPassword);
	}
	
	/**
	 * Tests the registration account with the quantity parameter.
	 */
	@Test
	public void registerAccountQuantityTest() {
		Mockito.doReturn(true).when(payPalGateway).registerAccount(username, password, quantity);
		Assert.assertTrue(payPalGateway.registerAccount(username, password, quantity));
		String newPassword = password + "test";
		Mockito.doReturn(false).when(payPalGateway).registerAccount(username, newPassword, quantity);
		Assert.assertFalse(payPalGateway.registerAccount(username, newPassword, quantity));
		String newUsername = username + "test";
		Mockito.doReturn(true).when(payPalGateway).registerAccount(newUsername, newPassword, quantity);
		payPalGateway.registerAccount(newUsername, newPassword, quantity);
	}

	/**
	 * Tests the payment
	 */
	@Test
	public void payTest() {
		Mockito.doReturn(true).when(payPalGateway).pay(username, password, quantity);
		Mockito.doReturn(false).when(payPalGateway).pay(username + "a", password, quantity);
		Mockito.doReturn(false).when(payPalGateway).pay(username, password + "a", quantity);
		Assert.assertTrue(payPalGateway.pay(username, password, quantity));
		Assert.assertFalse(payPalGateway.pay(username + "a", password, quantity));
		Assert.assertFalse(payPalGateway.pay(username, password + "a", quantity));
	}
}
