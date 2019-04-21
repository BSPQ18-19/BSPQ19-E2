package es.deusto.spq.payment.PayPal.data;

public class User {

	private String username;
	private String password;
	private float money;
	
	public User(String username, String password, float money) {
		super();
		this.username = username;
		this.password = password;
		this.money = money;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.money = 0;
	}
	
	public void pay(float amount) {
		money -= amount;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
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
