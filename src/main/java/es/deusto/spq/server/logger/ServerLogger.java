package es.deusto.spq.server.logger;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import org.apache.log4j.Logger;

/**
 * The logger manager for the server, so that there's only one log in the server.
 * It can be retrieved to log messages.
 * @author Iker
 *
 */
public class ServerLogger {

	/**
	 * The logger to be used in the server.
	 */
	private static final Logger log;
	
	static {
		log = Logger.getLogger("ServerLog");
	}
	
	/**
	 * Retrieves the logger to be used in the server.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
		return log;
	}
	
}
