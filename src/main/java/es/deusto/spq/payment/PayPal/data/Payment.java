package es.deusto.spq.payment.PayPal.data;

import java.sql.Timestamp;

public class Payment {

	private Timestamp timestamp;
	private float amount;
	
	public Payment(Timestamp timestamp, float amount) {
		super();
		this.timestamp = timestamp;
		this.amount = amount;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
