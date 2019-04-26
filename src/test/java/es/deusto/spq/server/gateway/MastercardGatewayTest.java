package es.deusto.spq.server.gateway;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MastercardGatewayTest {

	private static MastercardGateway mastercardGateway;
	private static long cardNumber;
	private static int securityCode;
	private static float amount;
	
	@BeforeClass
	public static void initialize() {
		mastercardGateway = Mockito.spy(new MastercardGateway());
		cardNumber = 123456789987654321L;
		securityCode = 12345679;
		amount = 10.5f;
	}
	
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
