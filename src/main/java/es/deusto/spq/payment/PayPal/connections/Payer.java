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

public class Payer extends Thread {

	private Socket client;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private Logger log;
	private DataBase dataBase;
	
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
			boolean result = dataBase.makePayment(username, password, payment);
			objectOutputStream.writeObject(result ? "OK" : "ERROR");
		} catch (Exception e) {
			log.fatal(e.getMessage());
		} finally {
			try {
				if(!client.isClosed())
					client.close();
				log.info("Payer closed");
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
			PayPal.removePayer(this);
		}
	}
	
	public void closePayer() {
		try {
			client.close();
		} catch (IOException e) {
			log.warn(e.getMessage());
		}
	}
	
}
