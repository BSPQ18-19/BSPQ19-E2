package es.deusto.spq.payment.mastercard.connections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.mastercard.Mastercard;

public class PayerTest {

	/** The payer class to be tested. */
	private static Payer payer;
	/** Instanced to load data. */
	@SuppressWarnings("unused")
	private static Mastercard mastercard;

	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		mastercard = new Mastercard();
		payer = new Payer(null, null, null);
	}
	
	/** Sets up the necessary data. */
	@Before
	public void setUp() {
		Mastercard.addPayer(payer);
	}
	
	/** Tests if the server listener closes correctly. */
	@Test
	public void payerTest() {
		payer.closePayer();
}
}
