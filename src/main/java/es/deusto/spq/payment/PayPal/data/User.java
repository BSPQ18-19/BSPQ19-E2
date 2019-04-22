package es.deusto.spq.payment.PayPal.data;

/**
 * The User data model the PayPal server uses.
 * @author Iker
 *
 */
public class User {

	/** The username of the User. */
	private String username;
	/** The password of the user. */
	private String password;
	/** The amount of money the user has. */
	private float money;
	
	/**
	 * Creates a new instance of the User.
	 * @param username - the username of the user.
	 * @param password - the password of this user account.
	 * @param money - the amount of money this user will have.
	 */
	public User(String username, String password, float money) {
		super();
		this.username = username;
		this.password = password;
		this.money = money;
	}

	/**
	 * Creates a new instance of the User. Since no parameter is specified for the money,
	 * is initialized to 0.
	 * @param username - the username of the user.
	 * @param password - the password of this user account.
	 */
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.money = 0;
	}
	
	/**
	 * Subtracts the amount of the payment to the current user's quantity. It does not
	 * check whether there's enough money to make the payment (the final amount will be
	 * negative in these cases). 
	 * @param amount - the amount of money to be subtracted.
	 */
	public void pay(float amount) {
		money -= amount;
	}
	
	/**
	 * Returns the username of this user account.
	 * @return a String with the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the password of this user account.
	 * @return a String with the password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Returns the amount of money this user has.
	 * @return a floating point number indicating the amount of money.
	 */
	public float getMoney() {
		return money;
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof User) {
			User u = (User) object;
			return u.getUsername().equals(username);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return username.hashCode();
	}
}
