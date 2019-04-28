package es.deusto.spq.payment.mastercard.logger;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class MastercardLoggerTest {

	/** Class initialized in order to load the class. */
	@SuppressWarnings("unused")
	private static MastercardLogger logger;
	
	/**
	 * Tests whetheter the MastercardLogger class initializes correctly the logger, as well 
	 * as it returns properly through the getter.
	 */
	@Test
	public void test() {
		Assert.assertEquals(MastercardLogger.getLogger().getClass(), Logger.class);
	}

}
