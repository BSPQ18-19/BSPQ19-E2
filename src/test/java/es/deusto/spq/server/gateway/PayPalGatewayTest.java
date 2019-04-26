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

	private static PayPalGateway payPalGateway;
	private static String username;
	private static String password;
	private static float quantity;
	
	@BeforeClass
	public static void initialize() {
		payPalGateway = Mockito.spy(new PayPalGateway());
		username = "username";
		password = "password";
		quantity = 5.0f;
	}
	
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
