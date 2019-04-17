package es.deusto.spq.payment.PayPal.logger;

import org.apache.log4j.Logger;

/**
 * The logger manager for the PayPal sever, so that there's only one log in this external
 * service. It can be retrieved to log messages.
 * @author Iker
 *
 */
public class PayPalLogger {

	/**
	 * The logger to be used in the PayPal server.
	 */
	private static final Logger log;
	
	static {
		log = Logger.getLogger("PayPalLog");
	}
	
	/**
	 * Retrieves the logger to be used in the PayPal server.
	 * @return a Logger object.
	 */
	public static Logger getLogger() {
		return log;
	}
	
}
