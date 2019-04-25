package es.deusto.spq.payment.mastercard.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.mastercard.logger.MastercardLogger;

/**
 * This class is a simplification of a DAO object and a real database. For simplification
 * purposes, no database has been created in this server. The amount of connections and users
 * the server will ever have will be small, so there won't be any problem storing the
 * data this class will store in memory. However, if this code is going to be used in a
 * scalable project, the approach mentioned at the beginning of the paragraph is recommended
 * to be followed.
 * 
 * @author Iker
 *
 */
public class DataBase {

	/** The only instance of the DataBase. */
	private static DataBase dataBase = new DataBase(); //Singleton instance
	/** The logger to log to. */
	private Logger log;
	/** A table of the credit cards and their payments. */
	private Map<CreditCard, List<Payment>> payments;
	/** A map mapping from credit cards' code to their credit card. */
	private Map<Long, CreditCard> cards;
	
	/**
	 * Creates the new instance of the database.
	 */
	private DataBase() {
		log = MastercardLogger.getLogger();
		payments = new HashMap<CreditCard, List<Payment>>();
		cards = new HashMap<Long, CreditCard>();
	}
	
	/**
	 * Returns the only instance of the database.
	 * @return the database.
	 */
	public static DataBase getDataBase() {
		return dataBase;
	}
	
	/**
	 * Returns the credit card associated to the code.
	 * @param code - the code of the credit card.
	 * @return the credit card if exists, and {@code null} if not.
	 */
	public CreditCard getCreditCard(long code) {
		return cards.get(code);
	}
	
	/**
	 * Adds the credit card to the database.
	 * @param creditCard - the credit card to be added.
	 * @return {@code true} if the credit card was successfully added, and
	 * 			{@code false} if not.
	 */
	public boolean addCreditCard(CreditCard creditCard) {
		if(creditCard == null || payments.containsKey(creditCard))
			return false;
		payments.put(creditCard, new ArrayList<Payment>());
		cards.put(creditCard.getCardNumber(), creditCard);
		return true;
	}
	
	/**
	 * Makes the payment to the credit card.
	 * @param creditCard - the credit card to pay.
	 * @param payment - the payment to be paid.
	 * @return {@code true} if the payment is processed, and
	 * 			{@code false} if not.
	 */
	public boolean makePayment(CreditCard creditCard, Payment payment) {
		if(creditCard == null || payment == null || !payments.containsKey(creditCard))
			return false;
		List<Payment> tmp = payments.get(creditCard);
		tmp.add(payment);
		payments.put(creditCard, tmp);
		return true;
	}
	
}
