package es.deusto.spq.server.gateway;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import es.deusto.spq.server.logger.ServerLogger;

public class PayPalGateway implements IPayPalGateway {

	/** The server socket to connect to. */
	private Socket server = null;
	/** The output stream to send objects. */
	private ObjectInputStream objectInputStream;
	/** The input stream to read objects from. */
	private ObjectOutputStream objectOutputStream;
	/** The IP address to connect to. */
	private String IP = "localhost";
	/** The port number to connect to. */
	private int port = 45000;
	/** The logger to log to. */
	private Logger log;
	/** The default timeout to read a message from the server. */
	public static final int SERVER_TIMEOUT = 20000;
	
	/**
	 * Creates a new instance of the PayPalGateway.
	 * <p>
	 * Since no IP and port are given, the default values are used: localhost:45000.
	 */
	public PayPalGateway() {
		log = ServerLogger.getLogger();
	}
	
	/**
	 * Creates a new instance of the PayPalGateway with the given server's parameters.
	 * @param IP - the IP to connect to.
	 * @param port - the port to connect to.
	 */
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
	
	/**
	 * Closes the connection with the current server.
	 */
	private void closeConnection() {
		try {
			server.close();
			server = null;
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
			} else if(message.equals("QUANTITY")) {
				log.warn("Server is configured to register with quantities");
				return false;
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
			Thread.sleep(10);
			message = (String) objectInputStream.readObject();
			if(!message.equals("USERNAME"))
				return false;
			objectOutputStream.writeObject(username);
			message = (String) objectInputStream.readObject();
			if(!message.equals("PASSWORD"))
				return false;
			objectOutputStream.writeObject(password);
			message = (String) objectInputStream.readObject();
			if(!message.equals("QUANTITY")) {
				if(message.equals("OK")) {
					log.warn("Created account with default quantity");
					return true;
				} else if(message.equals("ERROR")) {
					log.warn("Did not create a user");
					return false;
				}
			}
			objectOutputStream.writeObject(quantity);
			message = (String) objectInputStream.readObject();
			if(message.equals("OK")) {
				result = true;
				log.info("PayPal registration completed");
			}
		} catch (IOException | ClassNotFoundException e) {
			log.warn(e.getMessage());
			log.warn("Did not complete registration");
			return false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			objectOutputStream.writeObject(quantity);
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
