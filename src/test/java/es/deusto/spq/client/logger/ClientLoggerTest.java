package es.deusto.spq.client.logger;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the {@link es.deusto.spq.client.logger.ClientLogger} class.
 * @author Iker
 *
 */
public class ClientLoggerTest {

	/**
	 * Class initialized in order to load the initialization of the class.
	 */
	private static ClientLogger logger;
	
	/**
	 * Tests whether {@link es.deusto.spq.client.logger.ClientLogger} class initializes 
	 * correctly the logger, as well as it returns properly through the getter.
	 */
	@Test
	public void getLoggerTest() {
		Assert.assertEquals(ClientLogger.getLogger().getClass(), Logger.class);
	}

}
