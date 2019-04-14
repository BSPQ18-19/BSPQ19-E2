package es.deusto.spq.server.logger;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for the {@link es.deusto.spq.server.logger.ServerLogger} class.
 * @author Iker
 *
 */
public class ServerLoggerTest {

	/**
	 * Class initialized in order to load the initialization of the class.
	 */
	private static ServerLogger logger;
	
	/**
	 * Tests whether {@link es.deusto.spq.server.logger.ServerLogger} class initializes
	 * correctly the logger, as well as it returns properly through the getter.
	 */
	@Test
	public void getLoggerTest() {
		Assert.assertEquals(ServerLogger.getLogger().getClass(), Logger.class);
	}

}
