package es.deusto.spq.payment.PayPal.logger;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class PayPalLoggerTest {

	/** Class initialized in order to load the class. */
	private static PayPalLogger logger;
	
	/**
	 * Tests whetheter the PayPalLogger class initializes correctly the logger, as well 
	 * as it returns properly through the getter.
	 */
	@Test
	public void test() {
		Assert.assertEquals(PayPalLogger.getLogger().getClass(), Logger.class);
	}

}
