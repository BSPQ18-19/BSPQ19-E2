package es.deusto.spq.payment.PayPal.connections;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.PayPal.PayPal;

public class ServerListenerTest {

	/** An instance of the class to be tested. */
	private static ServerListener serverListener;
	/** Instanced to load data. */
	@SuppressWarnings("unused")
	private static PayPal paypal;
	
	/**
	 * Initializes the necessary data to execute the test.
	 * @throws IOException - launched by {@code java.net}.
	 */
	@BeforeClass
	public static void initialize() throws IOException {
		paypal = new PayPal();
		serverListener = new ServerListener(45000);
	}
	
	/** Sets up the necessary data. */
	@Before
	public void setUp() {
		PayPal.addListener(serverListener);
	}
	
	/** Tests if the server listener closes correctly. */
	@Test
	public void closeListenerTest() {
		serverListener.closeListener();
	}

}
