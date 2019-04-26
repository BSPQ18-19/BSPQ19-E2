package es.deusto.spq.payment.mastercard;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.BindException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.mastercard.connections.ServerListener;
import es.deusto.spq.payment.mastercard.connections.Payer;

public class MastercardTest {

	/** The port number to execute the server. */
	public static final int PORT = 45001;
	/** Instanced to load data. */
	private static Mastercard server;

	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		server = new Mastercard();
		try {
			server.start(PORT);
		} catch (BindException e) {
			/*
			 * There's no problem if a BindException happens.
			 * It means that another process is using that port.
			 */
		} catch (Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests the creation of a ServerListener class. If {@code BindException} is launched, 
	 * the test is marked as successful, since that exception is launched when another 
	 * process is using that port.
	 * @throws IOException - launched by {@code java.net}.
	 */
	@Test
	public void listenerTest() throws IOException {
		ServerListener listener = null;
		try {
			listener = new ServerListener(PORT);
			listener.closeListener();
			//It's OK if there's another process using that port
		} catch (BindException e) {}
		Mastercard.addListener(listener);
	}
	
	/**
	 * Tests the thread-pool of Payer.
	 */
	@Test
	public void payerTest() {
		Payer payer = new Payer(null, null, null);
		Mastercard.addPayer(payer);
		payer.closePayer();
	}
	
	
	/**
	 * Closes all the threads that have not been closed before.
	 */
	@AfterClass
	public static void closeServer() {
		Mastercard.closeServer();
}

}
