package es.deusto.spq.payment.mastercard.data;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class DataBaseTest {

	/** Initializes the database to load data. */
	private static DataBase dataBase;
	/** The creditcard to make payments to. */
	private static CreditCard creditcard;
	/** The number of the creditcard. */
	private static long number;
	/** The security code of the card. */
	private static int securityCode;
	/** The payment users want to make. */
	private static Payment payment;
	/** The amount of money the payment will cost. */
	private static float paymentAmount;
	
	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		dataBase = DataBase.getDataBase();
		number = 12345678998765431L;
		securityCode = 123456789;
		paymentAmount = 50.0f;
	}

	/**
	 * Tests whether the registration is successfully done.
	 */
	@Before
	public void registrationTest() {
		CreditCard creditcard = new CreditCard(number, securityCode);
		Assert.assertTrue(dataBase.addCreditCard(creditcard));
		payment = new Payment(paymentAmount);
	}
	
	/**
	 * Tests the the payment system to the database.
	 */
	@Test
	public void paymentTest() {
		Assert.assertTrue(dataBase.makePayment(creditcard, payment));
	}

}
