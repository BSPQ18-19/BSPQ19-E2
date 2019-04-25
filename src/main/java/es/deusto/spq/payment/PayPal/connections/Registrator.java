package es.deusto.spq.payment.PayPal.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.PayPal;
import es.deusto.spq.payment.PayPal.data.DataBase;
import es.deusto.spq.payment.PayPal.data.User;
import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

/**
 * <p>This is one type of the thread that will run on the PayPal server. The only aim of
 * this thread is to process the registration of a client. There's only one change to 
 * process it, if an error occurs (e.g. the user was already registered before), this 
 * thread is closed and a new thread must be generated. The server requests to the client
 * any information it needs; and at the end, it informs whether the registration has been
 * successfully processed (with an "OK" response), or not (with an "ERROR" response). 
 * However, to close the connection before it ends, the <code>closeRegistrator</code> method 
 * can also be used.</p>
 * 
 * <p>In order to initialize a Registrator thread, call Thread's <code>start</code> method. 
 * The thread is automatically closed when it finishes the registration process, including 
 * the removal of itself from {@link es.deusto.spq.payment.PayPal.PayPal}'s payers thread 
 * pool. Please note that the thread doesn't add itself to that pool.</p>
 * 
 * @author Iker
 *
 */
public class Registrator extends Thread {

	/** The socket of the client processing the payment. */
	private Socket client;
	/** The output stream to write data to the client. */
	private ObjectOutputStream objectOutputStream;
	/** The input stream to read data from the client. */
	private ObjectInputStream objectInputStream;
	/** The logger of the PayPal server, to log to. */
	private Logger log;
	/**
	 * The only instance of the {@link es.deusto.spq.payment.PayPal.data} class, which 
	 * emulates the database in the PayPal server. 
	 */
	private DataBase dataBase;
	
	/**
	 * Creates a new thread to process a registration in the server.
	 * @param client - the client that wants to process the payment.
	 * @param objectOutputStream - the ObjectOutputStream of the client's socket.
	 * @param objectInputStream - the ObjectInputStream of the client's socket.
	 */
	public Registrator(Socket client, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.log = PayPalLogger.getLogger();
		this.client = client;
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
		this.dataBase = DataBase.getDataBase();		
	}
	
	/**
	 * If false, only the username and password are requested, using the default amount
	 * of money (100).
	 * If true, requests the user to introduce the amount of money of the account.
	 */
	private boolean requestAmount = false;
	
	@Override
	public void run() {
		if(client == null)
			return;
		try {
			objectOutputStream.writeObject("USERNAME");
			String username = (String) objectInputStream.readObject();
			objectOutputStream.writeObject("PASSWORD");
			String password = (String) objectInputStream.readObject();
			User toBeRegistered;
			if(requestAmount) {
				objectOutputStream.writeObject("QUANTITY");
				float amount = objectInputStream.readFloat();
				toBeRegistered = new User(username, password, amount);
			} else {
				toBeRegistered = new User(username, password, 100);
			}
			boolean result = dataBase.registerUser(toBeRegistered);
			objectOutputStream.writeObject(result ? "OK" : "ERROR");
		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			try {
				if(!client.isClosed())
					client.close();
				log.info("Registrator closed");
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
			PayPal.removeRegistrator(this);
		}
	}
	
	/**
	 * Closes the connection with the client. If an IOException arises, it is logged to
	 * the logger. At the end, this thread is removed from the pool of Registrators in 
	 * {@link es.deusto.spq.payment.PayPal.PayPal} class.
	 */
	public void closeRegistrator() {
		if(client == null)
			return;
		try {
			client.close();
		} catch (IOException e) {
			log.warn(e.getMessage());
		} finally {
			PayPal.removeRegistrator(this);
		}
	}

}
