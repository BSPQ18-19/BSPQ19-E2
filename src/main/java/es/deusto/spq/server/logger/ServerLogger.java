package es.deusto.spq.server.logger;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
	private static Logger log;
	private static FileHandler fh;
	
	static {
		log = Logger.getLogger(ServerLogger.class.getName());
		try {
			fh = new FileHandler("log/ServerLog.log");
			log.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		} catch (Exception e) {
			log.severe("Cannot set the file handler for the logger. " + e.getMessage());
		} finally {
			log.setLevel(Level.ALL);
		}
	}
	
	/**
	 * Retrieves the logger to be used in the server.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
		return log;
	}
	
}
