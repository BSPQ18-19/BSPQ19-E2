package es.deusto.spq.client.logger;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import org.apache.log4j.Logger;

/**
 * The logger manager for the client, so that there's only one log in the client.
 * It can be retrieved to log messages.
 * @author Iker
 *
 */
public class ClientLogger {

	/**
	 * The logger to be used in the client.
	 */
	private static final Logger log;
	
	static {
		log = Logger.getLogger("ClientLog");
	}

	/**
	 * Retrieves the logger to be used in the client.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
		return log;
	}
	
}
