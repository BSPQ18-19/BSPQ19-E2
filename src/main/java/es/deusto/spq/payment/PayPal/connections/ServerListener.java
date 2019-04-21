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

public class ServerListener extends Thread {

	private Logger log;
	private ServerSocket server;
	private Socket client;
	
	public ServerListener(int port) throws IOException {
		log = PayPalLogger.getLogger();
		server = new ServerSocket(port);
		server.setReuseAddress(true);
	}
	
	private ObjectOutputStream objectOutputStream;
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
				
				switch(message) {
				case "REGISTER":
					Registrator registrator = new Registrator(client, objectOutputStream, objectInputStream);
					PayPal.addRegistrator(registrator);
					registrator.start();
					break;
				case "PAY":
					Payer payer = new Payer(client, objectOutputStream, objectInputStream);
					PayPal.addPayer(payer);
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
					log.info("Client listened.");
				} catch (IOException e) {
					log.warn("Error closing connection with client.");
				}
			}
		}
	}
	
	private boolean serverActive = true;
	
	public void closeListener() {
		serverActive = false;
		PayPal.removeListener(this);
	}

}
