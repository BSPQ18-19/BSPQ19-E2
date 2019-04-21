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

public class Registrator extends Thread {

	private Socket client;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private Logger log;
	private DataBase dataBase;
	
	public Registrator(Socket client, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.log = PayPalLogger.getLogger();
		this.client = client;
		this.objectOutputStream = objectOutputStream;
		this.objectInputStream = objectInputStream;
		this.dataBase = DataBase.getDataBase();		
	}
	
	@Override
	public void run() {
		try {
			objectOutputStream.writeObject("USERNAME");
			String username = (String) objectInputStream.readObject();
			objectOutputStream.writeObject("PASSWORD");
			String password = (String) objectInputStream.readObject();
//			objectOutputStream.writeObject("QUANTITY");
//			float amount = objectInputStream.readFloat();
			User user = new User(username, password, 100);
			boolean result = dataBase.registerUser(user);
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
	
	public void closeRegistrator() {
		try {
			client.close();
		} catch (IOException e) {
			log.warn(e.getMessage());
		}
	}

}
