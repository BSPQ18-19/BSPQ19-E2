package es.deusto.spq.server.gateway;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MastercardGatewayTest {

	/** The gateway to be tested. */
	private static MastercardGateway mastercardGateway;
	/** The card number of the mastercard card to be tested. */
	private static long cardNumber;
	/** The security code of the mastercard card to be tested. */
	private static int securityCode;
	/** The amount of money to perform tests. */
	private static float amount;
	
	/**
	 * Initializes all the data needed to perform tests.
	 */
	@BeforeClass
	public static void initialize() {
		mastercardGateway = Mockito.spy(new MastercardGateway());
		cardNumber = 123456789987654321L;
		securityCode = 12345679;
		amount = 10.5f;
	}
	
	/**
	 * Tests the payment
	 */
	@Test
	public void payTest() {
		Mockito.doReturn(true).when(mastercardGateway).pay(cardNumber, securityCode, amount);
		Assert.assertTrue(mastercardGateway.pay(cardNumber, securityCode, amount));
		Mockito.doReturn(true).when(mastercardGateway).pay(cardNumber, securityCode, amount + 5.0f);
		Assert.assertTrue(mastercardGateway.pay(cardNumber, securityCode, amount + 5.0f));
		Mockito.doReturn(false).when(mastercardGateway).pay(cardNumber + 2, securityCode, amount);
		Assert.assertFalse(mastercardGateway.pay(cardNumber + 2, securityCode, amount));
		Mockito.doReturn(false).when(mastercardGateway).pay(cardNumber, securityCode + 2, amount);
		Assert.assertFalse(mastercardGateway.pay(cardNumber, securityCode + 2, amount));
	}

}
