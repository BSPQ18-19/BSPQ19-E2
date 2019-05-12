package es.deusto.spq.payment.mastercard.logger;

import org.apache.log4j.Logger;

/**
 * The logger manager for the Mastercard sever, so that there's only one log in this external
 * service. It can be retrieved to log messages.
 * 
 * @author Iker
 *
 */
public class MastercardLogger {

	/**
	 * The logger to be used in the Mastercard server.
	 */
	private static final Logger log;
	
	static {
		log = Logger.getLogger("MastercardLog");
	}
	
	/**
	 * Retrieves the logger to be used in the Mastercard server.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
		return log;
	}
	
}
