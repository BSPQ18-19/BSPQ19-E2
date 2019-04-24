package es.deusto.spq.payment.PayPal.connections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.PayPal.PayPal;

public class PayerTest {

	/** The payer class to be tested. */
	private static Payer payer;
	/** Instanced to load data. */
	private static PayPal paypal;

	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		paypal = new PayPal();
		payer = new Payer(null, null, null);
	}
	
	/** Sets up the necessary data. */
	@Before
	public void setUp() {
		PayPal.addPayer(payer);
	}
	
	/** Tests if the server listener closes correctly. */
	@Test
	public void payerTest() {
		payer.closePayer();
	}
	
}
