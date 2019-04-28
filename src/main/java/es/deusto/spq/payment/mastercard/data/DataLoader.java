package es.deusto.spq.payment.mastercard.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

	private DataBase dataBase;
	
	public DataLoader(DataBase dataBase) {
		this.dataBase = dataBase;
		initializeData();
	}
	
	public void loadCreditCards() {
		dataBase.addCards(cards);
		dataBase.addPayments(payments);
	}
	
	private Map<CreditCard, List<Payment>> payments = new HashMap<CreditCard, List<Payment>>();
	private Map<Long, CreditCard> cards = new HashMap<Long, CreditCard>();
	
	private void initializeData() {
		// Card numbers initialization
		long num1 = 123456789L; int sec1 = 123;
		long num2 = 465789123L; int sec2 = 456;
		long num3 = 789123456L; int sec3 = 789;
		
		// Credit cards initialization
		CreditCard card1 = new CreditCard(num1, sec1);
		CreditCard card2 = new CreditCard(num2, sec2);
		CreditCard card3 = new CreditCard(num3, sec3);
		
		// Payments initialization
		Payment p1 = new Payment(5.0f);
		Payment p2 = new Payment(10.0f);
		Payment p3 = new Payment(12.99f);
		
		List<Payment> payments1 = new ArrayList<Payment>(); // No payments
		List<Payment> payments2 = new ArrayList<Payment>();
			payments2.add(p1);
		List<Payment> payments3 = new ArrayList<Payment>();
			payments3.add(p2);
			payments3.add(p3);
			
		//Add cards to the data structure
		cards.put(num1, card1);
		cards.put(num2, card2);
		cards.put(num3, card3);
		
		// Add payments to credit cards
		payments.put(card1, payments1);
		payments.put(card2, payments2);
		payments.put(card3, payments3);
	}
	
}
