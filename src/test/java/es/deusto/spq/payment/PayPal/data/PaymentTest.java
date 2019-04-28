package es.deusto.spq.payment.PayPal.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class PaymentTest {

	/** An instance of the class to be tested. */
	private static Payment payment;
	/** The timestamp of the payment. */
	private static Timestamp timestamp;
	/** The amount of money of the payment. */
	private static float amount;
	
	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		timestamp = Timestamp.valueOf(LocalDateTime.now());
		amount = 50.0f;
		payment = new Payment(timestamp, amount);
	}
	
	/** Sets up the necessary data. */
	@Before
	public void setUp() {
		payment = new Payment(timestamp, amount);
	}
	
	/**
	 * Tests whether the timestamp is set correclty - both the getter and setter.
	 */
	@Test
	public void timestampTest() {
		Timestamp current = payment.getTimestamp();
		try {
			//Increase the difference between both timestamps
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		Assert.assertTrue(now.after(current));
	}
	
	/**
	 * Tests whether the amount is set and get correctly.
	 */
	@Test
	public void amountTest() {
		Assert.assertEquals(amount, payment.getAmount());
		payment.setAmount(amount * 2 - 1);
		Assert.assertEquals(amount * 2 - 1, payment.getAmount());
	}

}
