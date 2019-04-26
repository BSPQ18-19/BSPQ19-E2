package es.deusto.spq.server.gateway;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import es.deusto.spq.server.logger.ServerLogger;

public class MastercardGateway implements IMastercardGateway {

	/** The server socket to connect to. */
	private Socket server = null;
	/** The output stream to send objects. */
	private ObjectInputStream objectInputStream;
	/** The input stream to read objects from. */
	private ObjectOutputStream objectOutputStream;
	/** The IP address to connect to. */
	private String IP = "localhost";
	/** The port number to connect to. */
	private int port = 45001;
	/** The logger to log to. */
	private Logger log;
	/** The default timeout to read a message from the server. */
	public static final int SERVER_TIMEOUT = 20000;
	
	/**
	 * Creates a new instance of the PayPalGateway.
	 * <p>
	 * Since no IP and port are given, the default values are used: localhost:45000.
	 */
	public MastercardGateway() {
		log = ServerLogger.getLogger();
	}
	
	/**
	 * Creates a new instance of the PayPalGateway with the given server's parameters.
	 * @param IP - the IP to connect to.
	 * @param port - the port to connect to.
	 */
	public MastercardGateway(String IP, int port) {
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
		log.info("Server connected to Mastercard");
	}
	
	/**
	 * Closes the connection with the current server.
	 */
	private void closeConnection() {
		try {
			server.close();
			log.info("Closed connection with Mastercard");
		} catch (IOException e) {
			log.warn("Could not close connection with Mastercard - " + e.getMessage());
		}
	}

	@Override
	public boolean pay(long cardNumber, int securityCode, float amount) {
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
			if(!message.equals("CARD_NUMBER"))
				return false;
			objectOutputStream.writeObject(cardNumber);
			message = (String) objectInputStream.readObject();
			if(!message.equals("SEC_CODE"))
				return false;
			objectOutputStream.writeObject(securityCode);
			message = (String) objectInputStream.readObject();
			if(message.equals("ERROR")) {
				result = true;
				log.info("Mastercar payment error - incorrect parameters.");
			} else if (message.equals("AMOUNT")){
				objectOutputStream.writeFloat(amount);
				message = (String) objectInputStream.readObject();
				if(!message.equals("OK")) {
					log.info("Mastercard payment completed");
					return true;
				}
			} else {
				log.warn("Unexpected server response");
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

}
