package es.deusto.spq.payment.PayPal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.connections.Payer;
import es.deusto.spq.payment.PayPal.connections.Registrator;
import es.deusto.spq.payment.PayPal.connections.ServerListener;
import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

public class PayPal {

	private Logger log;
	
	public static void main(String [] args) {
		int port = Integer.parseInt(args[0]);
		PayPal server = new PayPal();
		server.log = PayPalLogger.getLogger();
		try {
			server.start(port);
		} catch (IOException e) {
			server.log.fatal("Could not initialize PayPal server - " + e.getMessage());
		}
	}
	
	public PayPal() {
		activeListeners = new ArrayList<ServerListener>();
		activePayers = new ArrayList<Payer>();
		activeRegistrators = new ArrayList<Registrator>();
	}
	
	private static ReentrantLock listenerLock = new ReentrantLock();
	private static List<ServerListener> activeListeners;
	private static ReentrantLock payerLock = new ReentrantLock();
	private static List<Payer> activePayers;
	private static ReentrantLock registratorLock = new ReentrantLock();
	private static List<Registrator> activeRegistrators;
	
	public static void addListener(ServerListener listener) {
		listenerLock.lock();
		activeListeners.add(listener);
		listenerLock.unlock();
	}
	
	public static void removeListener(ServerListener listener) {
		listenerLock.lock();
		activeListeners.remove(listener);
		listenerLock.unlock();
	}
	
	public static void addPayer(Payer payer) {
		payerLock.lock();
		activePayers.add(payer);
		payerLock.unlock();
	}
	
	public static void removePayer(Payer payer) {
		payerLock.lock();
		activePayers.add(payer);
		payerLock.unlock();
	}
	
	public static void addRegistrator(Registrator registrator) {
		registratorLock.lock();
		activeRegistrators.add(registrator);
		registratorLock.unlock();
	}
	
	public static void removeRegistrator(Registrator registrator) {
		registratorLock.lock();
		activeRegistrators.add(registrator);
		registratorLock.unlock();
	}
	
	public void start(int port) throws IOException {
		ServerListener initialListener = new ServerListener(port);
		activeListeners.add(initialListener);
		initialListener.start();
	}
	
	public static void closeServer() {
		for(ServerListener serverListener : activeListeners)
			serverListener.closeListener();
		for(Payer payer : activePayers)
			payer.closePayer();
		for(Registrator registrator : activeRegistrators)
			registrator.closeRegistrator();
	}
	
}
