package es.deusto.spq.payment.mastercard.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.mastercard.Mastercard;
import es.deusto.spq.payment.mastercard.data.DataBase;
import es.deusto.spq.payment.mastercard.data.Payment;
import es.deusto.spq.payment.mastercard.data.CreditCard;
import es.deusto.spq.payment.mastercard.logger.MastercardLogger;

/**
 * This thread will run on the Mastercard server. The only aim of this thread is
 * to process the payment of a client. There's only one chance to process it, if an error occurs
 * (such as a wrong input on the card number), this thread is closed and a new thread must be
 * generated. The server requests to the client any information it needs; and at the end, it
 * informs whether the payment has been successfully processed (with an  "OK" response), or not
 * (with an "ERROR" response). However, the connection with the client can also be closed
 * through the {@code closePayer} method.
 * <p>
 * In order to initialize a Payer thread, call Thread's {@code start} method. The thread
 * is automatically closed when it finishes the payment process, including the removal of
 * itself from {@link es.deusto.spq.payment.mastercard.Mastercard}'s payers thread list. Please note
 * that the thread doesn't add itself to that pool.
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
	/** The logger of the Mastercard server, to log to. */
	private Logger log;
	/**
	 * The only instance of the {@link es.deusto.spq.payment.mastercard.data.DataBase} class, which 
	 * emulates the database in the Mastercard server.
	 */
	private DataBase dataBase;
	
	/**
	 * Creates a new thread to process a payment in the server.
	 * @param client - the client that wants to process the payment.
	 * @param objectOutputStream - the ObjectOutputStream of the client's socket.
	 * @param objectInputStream - the ObjectInputStream of the client's socket.
	 */
	public Payer(Socket client, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.log = MastercardLogger.getLogger();
		this.client = client;
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
		dataBase = DataBase.getDataBase();
	}
	
	@Override
	public void run() {
		if(client == null)
			return;
		try {
			log.debug("Started client payment...");
			objectOutputStream.writeObject("CARD_NUMBER");
			Long cardNumber = (Long) objectInputStream.readObject();
			CreditCard creditCard = dataBase.getCreditCard(cardNumber);
			objectOutputStream.writeObject("SEC_CODE");
			int sec = (int) objectInputStream.readObject();
			if(creditCard.getCardSecurityCode() != sec) {
				objectOutputStream.writeObject("ERROR");
				return;
			}
			objectOutputStream.writeObject("AMOUNT");
			float amount = (float) objectInputStream.readObject();
			Payment payment = new Payment(amount);
			boolean result = dataBase.makePayment(creditCard, payment);
			objectOutputStream.writeObject(result ? "OK" : "ERROR");
		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			closePayer();
		}
	}
	
	/**
	 * Closes the connection with the client. If an IOException arises, it is logged to
	 * the logger. At the end, this thread is removed from the pool of Payers in
	 * {@link es.deusto.spq.payment.mastercard.Mastercard} class.
	 */
	public void closePayer() {
		try {
			if(client != null && !client.isClosed())
				client.close();
		} catch (IOException e) {
			log.warn(e.getMessage());
		} finally {
			Mastercard.removePayer(this);
			interrupt();
			log.info("Payer closed");
		}
	}
	
}