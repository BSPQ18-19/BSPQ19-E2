package es.deusto.spq.payment.PayPal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import es.deusto.spq.payment.PayPal.connections.Payer;
import es.deusto.spq.payment.PayPal.connections.Registrar;
import es.deusto.spq.payment.PayPal.connections.ServerListener;
import es.deusto.spq.payment.PayPal.logger.PayPalLogger;

/**
 * <p>The main class of the PayPal server. The whole server is initialized, including the
 * pools of the new threads that have not been closed. Nevertheless, the initialization
 * can also be done from another class through the <code>start</code> method. In order
 * to shut down the server, call the <code>closeServer</code> method. Thread pools that
 * keep track of the active or waiting threads are thread-safe.</p>
 * 
 * <p>The server will run in the port on the first argument, no other arguments are needed
 * (not checked, ignored).</p>
 * 
 * @author Iker
 *
 */
public class PayPal {

	/** The logger to log to, just for the PayPal's server. */
	private static Logger log;
	
	public static void main(String [] args) {
		int port = Integer.parseInt(args[0]);
		PayPal server = new PayPal();
		try {
			// Initializes the execution of the server.
			server.start(port);
		} catch (IOException e) {
			log.fatal("Could not initialize PayPal server - " + e.getMessage());
		}
	}
	
	/**
	 * Creates the instance of the PayPal server. The pools of threads are static and
	 * initialized in this constructor, so creating a new instance would clear all the
	 * lists.
	 */
	public PayPal() {
		log = PayPalLogger.getLogger();
		activeListeners = new ArrayList<ServerListener>();
		activePayers = new ArrayList<Payer>();
		activeRegistrars = new ArrayList<Registrar>();
	}
	
	/**
	 * A lock to ensure mutual exclusion between threads that want to either add or
	 * remove the an active listener to the pool.
	 */
	private static ReentrantLock listenerLock = new ReentrantLock();
	/** The pool of active listeners in the server (both ready or running). */
	private static List<ServerListener> activeListeners;
	/**
	 * A lock to ensure mutual exclusion between threads that want to either add or
	 * remove the an active payer to the pool.
	 */
	private static ReentrantLock payerLock = new ReentrantLock();
	/** The pool of active payers in the server (both ready and running). */
	private static List<Payer> activePayers;
	/**
	 * A lock to ensure mutual exclusion between threads that want to either add or
	 * remove the an active listener to the pool.
	 */
	private static ReentrantLock registrarLock = new ReentrantLock();
	/** The pool of active registrars in the server (both ready and running). */
	private static List<Registrar> activeRegistrars;
	
	/**
	 * Adds a ServerListener to its pool.
	 * Note that this method may take some time to do that, since it may be waiting
	 * for the execution ending of another thread.
	 * @param listener - the listener to be added.
	 * @return {@code true} if the server accepts the thread, and {@code false} if not.
	 */
	public static boolean addListener(ServerListener listener) {
		if(closingServer)
			return false;
		if(listener != null) {
			listenerLock.lock();
			activeListeners.add(listener);
			listenerLock.unlock();
		}
		return true;
	}
	
	/**
	 * Removes a ServerListener from its pool.
	 * Note that this method may take some time to do that, since it may be waiting
	 * for the execution ending of another thread.
	 * @param listener - the listener to be removed.
	 */
	public static void removeListener(ServerListener listener) {
		if(listener == null)
			return;
		listenerLock.lock();
		activeListeners.remove(listener);
		listenerLock.unlock();
	}
	
	/**
	 * Adds a Payer to its pool.
	 * Note that this method may take some time to do that, since it may be waiting
	 * for the execution ending of another thread.
	 * @param payer - the payer to be added.
	 * @return {@code true} if the server accepts the thread, and {@code false} if not.
	 */
	public static boolean addPayer(Payer payer) {
		if(closingServer)
			return false;
		if(payer != null) {
			payerLock.lock();
			activePayers.add(payer);
			payerLock.unlock();
		}
		return true;
	}
	
	/**
	 * Removes a Payer from its pool.
	 * Note that this method may take some time to do that, since it may be waiting
	 * for the execution ending of another thread.
	 * @param payer - the payer to be removed.
	 */
	public static void removePayer(Payer payer) {
		if(payer == null)
			return;
		payerLock.lock();
		activePayers.add(payer);
		payerLock.unlock();
	}
	
	/**
	 * Adds a Registrar to its pool.
	 * Note that this method may take some time to do that, since it may be waiting
	 * for the execution ending of another thread.
	 * @param registrator - the registrar to be added.
	 * @return {@code true} if the server accepts the thread, and {@code false} if not.
	 */
	public static boolean addRegistrar(Registrar registrator) {
		if(closingServer)
			return false;
		if(registrator != null) {
			registrarLock.lock();
			activeRegistrars.add(registrator);
			registrarLock.unlock();
		}
		return true;
	}
	
	/**
	 * Removes a ServerListener from its pool.
	 * Note that this method may take some time to do that, since it may be waiting
	 * for the execution ending of another thread.
	 * @param registrator - the registrar to be removed.
	 */
	public static void removeRegistrar(Registrar registrator) {
		if(registrator == null)
			return;
		registrarLock.lock();
		activeRegistrars.add(registrator);
		registrarLock.unlock();
	}
	
	/**
	 * Starts the execution of the PayPal server.
	 * @param port - the port this server will be using.
	 * @throws IOException - may be launched by the connection.
	 */
	public void start(int port) throws IOException {
		ServerListener initialListener = new ServerListener(port);
		activeListeners.add(initialListener);
		initialListener.start();
	}
	
	/** If {@code true} new threads are no longer accepted. */
	private static boolean closingServer = false;
	
	/**
	 * Closes all the threads in the pool, both the active and ready ones.
	 */
	public static void closeServer() {
		closingServer = true;
		List<ServerListener> listenersToBeRemoved = new ArrayList<ServerListener>();
		listenerLock.lock();
		for(ServerListener serverListener : activeListeners)
			listenersToBeRemoved.add(serverListener);
		listenerLock.unlock();
		for(ServerListener l : listenersToBeRemoved)
			l.closeListener();
		log.info("All ServerListener closed");
		List<Payer> payersToBeRemoved = new ArrayList<Payer>();
		payerLock.lock();
		for(Payer payer : activePayers)
			payersToBeRemoved.add(payer);
		payerLock.unlock();
		for(Payer p : payersToBeRemoved)
			p.closePayer();
		log.info("All Payer closed");
		List<Registrar> registrarsToBeRemoved = new ArrayList<Registrar>();
		registrarLock.lock();
		for(Registrar registrars : activeRegistrars)
			registrarsToBeRemoved.add(registrars);
		for(Registrar registrar : registrarsToBeRemoved)
			registrar.closeRegistrar();
		registrarLock.unlock();
		log.info("All Registrar closed");
		log.info("PayPal server is closed and shut down...");
	}
	
}
