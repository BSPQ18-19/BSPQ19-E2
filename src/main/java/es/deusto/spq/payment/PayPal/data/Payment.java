package es.deusto.spq.payment.PayPal.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Payment {

	private Timestamp timestamp;
	private float amount = 0;
	
	public Payment(Timestamp timestamp, float amount) {
		super();
		this.timestamp = timestamp;
		this.amount = amount;
	}
	
	public Payment(float amount) {
		super();
		this.timestamp = Timestamp.valueOf(LocalDateTime.now());
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
