package es.deusto.spq.client.logger;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
	private static Logger log;
	private static FileHandler fh;
	
	static {
		log = Logger.getLogger(ClientLogger.class.getName());
		try {
			fh = new FileHandler("log/ClientLog.log");
			log.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		} catch (Exception e) {
			log.severe("Cannot set the file handler for the logger. " + e.getMessage());
		} finally {
			log.setLevel(Level.ALL);
		}
	}

	/**
	 * Retrieves the logger to be used in the client.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
		return log;
	}
	
}
