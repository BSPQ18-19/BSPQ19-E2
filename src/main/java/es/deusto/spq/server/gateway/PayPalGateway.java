package es.deusto.spq.server.gateway;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import es.deusto.spq.server.logger.ServerLogger;

public class PayPalGateway implements IPayPalGateway {

	private Socket server = null;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private String IP = "localhost";
	private int port = 45000;
	private Logger log;
	public static final int SERVER_TIMEOUT = 20000;
	
	public PayPalGateway(String IP, int port) {
		this.IP = IP;
		this.port = port;
		log = ServerLogger.getLogger();
	}
	
	/**
	 * Establishes the connection with the server in the IP and port provided.
	 * @param ip - IP to establish connection to.
	 * @param port - port to establish connection to.
	 * @throws UnknownHostException - launched by {@code java.net.Socket}.
	 * @throws IOException - launched by {@code java.net.Socket}
	 */
	private void establishConnection(String ip, int port) throws UnknownHostException, IOException {
		server = new Socket(IP, port);
		server.setSoTimeout(SERVER_TIMEOUT);
		server.setReuseAddress(true);
		objectInputStream = new ObjectInputStream(server.getInputStream());
		objectOutputStream = new ObjectOutputStream(server.getOutputStream());
		log.info("Server connected to PayPal");
	}
	
	private void closeConnection() {
		try {
			server.close();
			log.info("Closed connection with PayPal");
		} catch (IOException e) {
			log.warn("Could not close connection with PayPal - " + e.getMessage());
		}
	}

	@Override
	public boolean registerAccount(String username, String password) {
		if(server == null || !server.isConnected()) {
			try {
				establishConnection(IP, port);
			} catch (Exception e) {
				log.warn(e.getMessage());
				return false;
			}
		}
		String message;
		boolean result = false;
		try {
			objectOutputStream.writeObject("REGISTER");
			message = (String) objectInputStream.readObject();
			if(!message.equals("USERNAME"))
				return false;
			objectOutputStream.writeObject(username);
			message = (String) objectInputStream.readObject();
			if(!message.equals("PASSWORD"))
				return false;
			objectOutputStream.writeObject(password);
			message = (String) objectInputStream.readObject();
			if(message.equals("OK")) {
				result = true;
				log.info("PayPal registration completed");
			}
		} catch (IOException | ClassNotFoundException e) {
			log.warn(e.getMessage());
			log.warn("Did not complete registration");
			return false;
		} finally {
			closeConnection();
		}
		return result;
	}

	@Override
	public boolean registerAccount(String username, String password, float quantity) {
		if(server == null || !server.isConnected()) {
			try {
				establishConnection(IP, port);
			} catch (Exception e) {
				log.warn(e.getMessage());
				return false;
			}
		}
		String message;
		boolean result = false;
		try {
			objectOutputStream.writeObject("REGISTER");
			message = (String) objectInputStream.readObject();
			if(!message.equals("USERNAME"))
				return false;
			objectOutputStream.writeObject(username);
			message = (String) objectInputStream.readObject();
			if(!message.equals("PASSWORD"))
				return false;
			objectOutputStream.writeObject(password);
			message = (String) objectInputStream.readObject();
			if(!message.equals("QUANTITY"))
				return false;
			objectOutputStream.writeFloat(quantity);
			message = (String) objectInputStream.readObject();
			if(message.equals("OK")) {
				result = true;
				log.info("PayPal registration completed");
			}
		} catch (IOException | ClassNotFoundException e) {
			log.warn(e.getMessage());
			log.warn("Did not complete registration");
			return false;
		} finally {
			closeConnection();
		}
		return result;
	}

	@Override
	public boolean pay(String username, String password, float quantity) {
		if(server == null || !server.isConnected()) {
			try {
				establishConnection(IP, port);
			} catch (Exception e) {
				log.warn(e.getMessage());
				return false;
			}
		}
		String message;
		boolean result = false;
		try {
			objectOutputStream.writeObject("PAY");
			message = (String) objectInputStream.readObject();
			if(!message.equals("USERNAME"))
				return false;
			objectOutputStream.writeObject(username);
			message = (String) objectInputStream.readObject();
			if(!message.equals("PASSWORD"))
				return false;
			objectOutputStream.writeObject(password);
			message = (String) objectInputStream.readObject();
			if(!message.equals("PRICE"))
				return false;
			objectOutputStream.writeFloat(quantity);
			message = (String) objectInputStream.readObject();
			if(message.equals("OK")) {
				result = true;
				log.info("PayPal payment completed");
			}
		} catch (IOException | ClassNotFoundException e) {
			log.warn("Did not complete the payment - " + e.getMessage());
			return false;
		} finally {
			closeConnection();
		}
		return result;
	}

}
