package es.deusto.spq.server.gateway;

public interface IMastercardGateway {
	
	/**
	 * Pays the quantity to the given mastercard card.
	 * @param cardNumber - the card number of the given card.
	 * @param securityCode - the security code of the given card.
	 * @param amount - the amount of money of the given card.
	 * @return {@code true} if the payment is successfully done, and 
	 * 			{@code false} if not.
	 */
	public boolean pay(long cardNumber, int securityCode, float amount);
	
}
