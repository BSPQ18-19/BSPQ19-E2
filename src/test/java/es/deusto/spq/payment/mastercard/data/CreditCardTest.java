package es.deusto.spq.payment.mastercard.data;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class CreditCardTest {

	/** The creditcard instance to be tested. */
	private CreditCard creditcard;
	/** The number of the credit card. */
	private static long number;
	/** The security code of the credit card. */
	private static int securityCode;

	/**
	 * Initializes all the data to run the tests in this class.
	 */
	@BeforeClass
	public static void creditCardTest() {
		number = 12345678998765421L;
		securityCode = 123456789;
	}
	
	/**
	 * Sets up the data to run the tests. 
	 */
	@Before
	public void setUp() {
		creditcard = new CreditCard(number, securityCode);
	}
	
	/**
	 * Tests whether payments are done correctly.
	 */
	@Test
	public void makePaymentTest() {
		float amount = 15.5f;
		float before = creditcard.getTotalDebt();
		creditcard.makePayment(amount);
		Assert.assertEquals(before - amount, creditcard.getTotalDebt());
	}
	
	/**
	 * Test the card number.
	 */
	@Test
	public void cardNumberTest() {
		Assert.assertEquals(number, creditcard.getCardNumber());
	}
	
	/**
	 * Tests the security code.
	 */
	@Test
	public void securityCodeTest() {
		Assert.assertEquals(securityCode, creditcard.getCardSecurityCode());
	}
	
}
