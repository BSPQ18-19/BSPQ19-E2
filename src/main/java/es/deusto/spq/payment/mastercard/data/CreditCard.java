package es.deusto.spq.payment.mastercard.data;

import java.util.List;

/**
 * The credit card data model the Mastercard server uses.
 * 
 * @author Iker
 *
 */
public class CreditCard {

	/** The credit card's number. */
	private long number;
	/** The security code of the credit card. */
	private int securityCode;
	/** The total debt of this credit card, in absolute value (always more or equals to 0). */
	private float totalDebt = 0;
	
	/**
	 * Creates a new CreditCard.
	 * @param cardNumber - the number of the credit card.
	 * @param cardSecurtyCode - the CSC of the credit card.
	 */
	public CreditCard(long cardNumber, int cardSecurtyCode) {
		super();
		this.number = cardNumber;
		this.securityCode = cardSecurtyCode;
	}
	
	/**
	 * Makes the payment for this credit card.
	 * @param amount - the amount of the payment to be done.
	 */
	public void makePayment(float amount) {
		totalDebt += amount;
	}
	
	/**
	 * Returns the credit card's number.
	 * @return the number
	 */
	public long getCardNumber() {
		return number;
	}

	/**
	 * Returns the credit card's security code.
	 * @return the code.
	 */
	public int getCardSecurityCode() {
		return securityCode;
	}

	/**
	 * Returns the total debt of payments done with this credit card.
	 * @return - the total debt.
	 */
	public float getTotalDebt() {
		return totalDebt;
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", securityCode=" + securityCode + "]";
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof CreditCard) {
			CreditCard u = (CreditCard) object;
			return u.getCardNumber() == number;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) number;
	}
}
