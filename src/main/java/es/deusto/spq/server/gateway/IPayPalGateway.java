package es.deusto.spq.server.gateway;

public interface IPayPalGateway {

	/**
	 * Establishes the connection with the server in the IP and port provided.
	 * @param ip - IP to establish connection to.
	 * @param port - port to establish connection to.
	 */
	public void establishConnection(String ip, int port);
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
	 * Pays the quantity to the given PayPal account.
	 * @param username - the username of the account.
	 * @param password - the password of the account.
	 * @param quantity - the quantity of the account.
	 * @return {@code true} if the payment is successfully done, and 
	 * 			{@code false} if not.
	 */
	public boolean pay(String username, String password, float quantity);
	
}
