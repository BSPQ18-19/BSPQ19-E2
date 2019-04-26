package es.deusto.spq.server.gateway;

import java.io.IOException;
import java.net.UnknownHostException;

public interface IPayPalGateway {

	/**
	 * Registers the given account in the PayPal server.
	 * @param username - the username of the new account.
	 * @param password - the password of the new account.
	 * @return {@code true} if the account registers correclty,
	 * 			{@code false} if there is an error.
	 */
	public boolean registerAccount(String username, String password);
	/**
	 * Registers the given account in the PayPal server.
	 * @param username - the username of the new account.
	 * @param password - the password of the new account.
	 * @param quantity - the quantity of the new account.
	 * @return {@code true} if the account registers correclty, and 
	 * 			{@code false} if there is an error.
	 */
	public boolean registerAccount(String username, String password, float quantity);
	/**
	 * Pays the quantity to the given PayPal account. Note that there must be enabled the 
	 * option to add a quantity in the server (if it isn't, {@code false} will be returned.
	 * @param username - the username of the account.
	 * @param password - the password of the account.
	 * @param quantity - the quantity of the account.
	 * @return {@code true} if the payment is successfully done, and 
	 * 			{@code false} if not.
	 */
	public boolean pay(String username, String password, float quantity);
	
}
