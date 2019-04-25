package es.deusto.spq.payment.mastercard.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Payment data model the Mastercard server uses.
 * 
 * @author Iker
 *
 */
public class Payment {

	/** Timestamp of the payment request received in the server. */
	private Timestamp timestamp;
	/** Amount of money the payment costs. */
	private float amount = 0;
	
	/**
	 * Creates a new instance of the Payment.
	 * @param timestamp - the Timestamp of the payment request received in the server.
	 * @param amount - the amount of money the payment costs
	 */
	public Payment(Timestamp timestamp, float amount) {
		super();
		this.timestamp = timestamp;
		this.amount = amount;
	}
	
	/**
	 * Creates a new instance of the Payment.
	 * @param amount - the amount of money the payment costs
	 */
	public Payment(float amount) {
		super();
		this.timestamp = Timestamp.valueOf(LocalDateTime.now());
		this.amount = amount;
	}
	
	/**
	 * Returns the timestamp of the payment.
	 * @return the Timestamp of the payment.
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Updates the timestamp of a payment.
	 * @param timestamp - the updated Timestamp of the payment. 
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Returns the amount of money this payment costs.
	 * @return a floating point number indicating the amount of money.
	 */
	public float getAmount() {
		return amount;
	}
	
	/**
	 * Updates the amount of money this payment costs.
	 * @param amount - the updated amount of money.
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
