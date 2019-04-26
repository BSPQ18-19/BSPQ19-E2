package es.deusto.spq.payment.PayPal;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.BindException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.deusto.spq.payment.PayPal.connections.Payer;
import es.deusto.spq.payment.PayPal.connections.Registrar;
import es.deusto.spq.payment.PayPal.connections.ServerListener;

public class PayPalTest {

	/** The port number to execute the server. */
	public static final int PORT = 45000;
	/** Instanced to load data. */
	private static PayPal server;

	/**
	 * Initializes the necessary data to execute the test.
	 */
	@BeforeClass
	public static void initialize() {
		server = new PayPal();
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
		PayPal.addListener(listener);
	}
	
	/**
	 * Tests the thread-pool of Payer.
	 */
	@Test
	public void payerTest() {
		Payer payer = new Payer(null, null, null);
		PayPal.addPayer(payer);
		payer.closePayer();
	}
	
	
	/**
	 * Tests the thread-pool of Registrator. 
	 */
	@Test
	public void registratorTest() {
		Registrar registrator = new Registrar(null, null, null);
		PayPal.addRegistrar(registrator);
		registrator.closeRegistrar();
	}
	
	/**
	 * Closes all the threads that have not been closed before.
	 */
	@AfterClass
	public static void closeServer() {
		PayPal.closeServer();
	}
	
}
