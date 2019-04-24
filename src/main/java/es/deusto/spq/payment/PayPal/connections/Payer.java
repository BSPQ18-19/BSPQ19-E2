package es.deusto.spq.payment.PayPal.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.PayPal;
import es.deusto.spq.payment.PayPal.data.DataBase;
import es.deusto.spq.payment.PayPal.data.Payment;
import es.deusto.spq.payment.PayPal.data.User;
import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

/**
 * <p>This is one type of thread that will run on the PayPal server. The only aim of this thread is
 * to process the payment of a client. There's only one chance to process it, if an error occurs
 * (such as a wrong input on the password), this thread is closed and a new thread must be
 * generated. The server requests to the client any information it needs; and at the end, it
 * informs whether the payment has been successfully processed (with an  "OK" response), or not
 * (with an "ERROR" response). However, the connection with the client can also be closed
 * through the <code>closePayer</code> method.</p>
 * 
 * <p>In order to initialize a Payer thread, call Thread's <code>start</code> method. The thread
 * is automatically closed when it finishes the payment process, including the removal of
 * itself from {@link es.deusto.spq.payment.PayPal.PayPal}'s payers thread list. Please note
 * that the thread doesn't add itself to that pool.</p>
 * 
 * @author Iker
 *
 */
public class Payer extends Thread {

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
	 * Creates a new thread to process a payment in the server.
	 * @param client - the client that wants to process the payment.
	 * @param objectOutputStream - the ObjectOutputStream of the client's socket.
	 * @param objectInputStream - the ObjectInputStream of the client's socket.
	 */
	public Payer(Socket client, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.log = PayPalLogger.getLogger();
		this.client = client;
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
		dataBase = DataBase.getDataBase();
	}
	
	@Override
	public void run() {
		try {
			objectOutputStream.writeObject("USERNAME");
			String username = (String) objectInputStream.readObject();
			objectOutputStream.writeObject("PASSWORD");
			String password = (String) objectInputStream.readObject();
			objectOutputStream.writeObject("PRICE");
			float amount = objectInputStream.readFloat();
			Payment payment = new Payment(amount);
			// Does not ask to input again the payment. If it's wrong, an error arises.
			boolean result = dataBase.makePayment(username, password, payment);
			objectOutputStream.writeObject(result ? "OK" : "ERROR");
		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			// Always closes the connection at the end.
			try {
				if(!client.isClosed())
					client.close();
				log.info("Payer closed");
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
			// Removes itself from the Payer-pool in the PayPal class.
			PayPal.removePayer(this);
		}
	}
	
	/**
	 * Closes the connection with the client. If an IOException arises, it is logged to
	 * the logger. At the end, this thread is removed from the pool of Payers in
	 * {@link es.deusto.spq.payment.PayPal.PayPal} class.
	 */
	public void closePayer() {
		try {
			client.close();
		} catch (IOException e) {
			log.warn(e.getMessage());
		} finally {
			PayPal.removePayer(this);
		}
	}
	
}
