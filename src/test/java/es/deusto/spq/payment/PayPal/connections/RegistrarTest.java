package es.deusto.spq.payment.PayPal.connections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.PayPal.PayPal;

public class RegistrarTest {

	/** The registrator class to be tested. */
	private static Registrar registrator;
	/** Instanced to load data. */
	@SuppressWarnings("unused")
	private static PayPal paypal;
	
	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		paypal = new PayPal();
		registrator = new Registrar(null, null, null);
	}
	
	/** Sets up the necessary data. */
	@Before
	public void setUp() {
		PayPal.addRegistrar(registrator);
	}
	
	/** Tests if the server listener closes correctly. */
	@Test
	public void closeRegistratorTest() {
		registrator.closeRegistrar();
	}

}
