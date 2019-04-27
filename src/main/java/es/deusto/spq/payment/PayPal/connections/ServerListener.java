package es.deusto.spq.payment.PayPal.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.PayPal;
import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

/**
 * <p>This is one type of thread that will run on the PayPal server. The only aim of this
 * thread is to wait for clients and create new threads for the registration and payment 
 * (no thread is created if the option is not valid). There's only one change to submit 
 * the proper option: if it is valid a new thread will be created and the client will 
 * continue its conversation with that new thread; but if the option is not available, 
 * an "ERROR" message will be sent to the client and the connection will be closed.</p>
 * 
 * <p>{@link es.deusto.spq.payment.PayPal.connections.Payer} to process the payment and 
 * {@link es.deusto.spq.payment.PayPal.connections.Registrar} to register new users are
 * the only options that can be executed.</p>
 * 
 * <p>In order to initialize a ServerListener, the port number must be provided (to be 
 * used by the server to wait for connections). After the initialization, the Thread's 
 * <code>start</code> method must be called to begin the execution.</p>
 * 
 * <p>Finally, <code>closeListener</code> method can be used to close this thread. Although 
 * this thread does not include itself in {@link es.deusto.spq.payment.PayPal.PayPal}'s 
 * listeners thread list, it removes itself from it before it ends the execution.</p>
 * 
 * @author Iker
 *
 */
public class ServerListener extends Thread {

	/** The logger to log to in the PayPal server. */
	private Logger log;
	/** The ServerSocket of this ServerListener. */
	private ServerSocket server;
	/** The client that establishes the connection to this server. */
	private Socket client;
	
	/**
	 * Creates a new instance of the current ServerListener. In order to get it started,
	 * the Thread's <code>start</code> method must be called.
	 * @param port - the port to be used by the server.
	 * @throws IOException - launched by the connection.
	 */
	public ServerListener(int port) throws IOException {
		log = PayPalLogger.getLogger();
		server = new ServerSocket(port);
		server.setReuseAddress(true);
	}
	
	/** The output stream to write data. */
	private ObjectOutputStream objectOutputStream;
	/** The input stream of the client to read data. */
	private ObjectInputStream objectInputStream;
	
	
	@Override
	public void run() {
		log.info("New ServerListener active and waiting on port " + server.getLocalPort() + "...");
		while(serverActive) {
			try {
				client = server.accept();
				log.info("New client accepted. Remote address: " + client.getRemoteSocketAddress().toString());
				client.setSoTimeout(20000);
				objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				objectInputStream = new ObjectInputStream(client.getInputStream());
				
				String message = (String) objectInputStream.readObject();
				
				// The options the client has with its connection.
				switch(message) {
				case "REGISTER":
					Registrar registrator = new Registrar(client, objectOutputStream, objectInputStream);
					if(PayPal.addRegistrar(registrator))
						registrator.start();
					break;
				case "PAY":
					Payer payer = new Payer(client, objectOutputStream, objectInputStream);
					if(PayPal.addPayer(payer))
						payer.start();
					break;
				default:
					objectOutputStream.writeObject("ERROR");
					break;
				}
			} catch (Exception e) {
				log.fatal(e.getMessage());
			} finally {
				try {
					if(!client.isClosed())
						client.close();
				} catch (IOException e) {
					log.warn("Error closing connection with client.");
				} finally {
					closeListener();
				}
			}
		}
	}
	
	/** This thread will be active till this boolean is switched off. */
	private boolean serverActive = true;
	
	/**
	 * Closes this thread. If there's a client being processed, it will be finished before
	 * closing the server; i.e. it won't accept more clients. Further, the instance removes
	 * itself from the pool of ServerListeners before ending the execution..
	 */
	public void closeListener() {
		serverActive = false;
		PayPal.removeListener(this);
		interrupt();
		log.info("ServerListener closed");
	}

}
