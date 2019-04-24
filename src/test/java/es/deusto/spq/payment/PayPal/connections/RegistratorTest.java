package es.deusto.spq.payment.PayPal.connections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.PayPal.PayPal;

public class RegistratorTest {

	/** The registrator class to be tested. */
	private static Registrator registrator;
	/** Instanced to load data. */
	private static PayPal paypal;
	
	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		paypal = new PayPal();
		registrator = new Registrator(null, null, null);
	}
	
	/** Sets up the necessary data. */
	@Before
	public void setUp() {
		PayPal.addRegistrator(registrator);
	}
	
	/** Tests if the server listener closes correctly. */
	@Test
	public void closeRegistratorTest() {
		registrator.closeRegistrator();
	}

}
