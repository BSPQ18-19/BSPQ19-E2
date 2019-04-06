package es.deusto.spq.server;

import es.deusto.spq.server.locale.LocaleManager;
import es.deusto.spq.server.locale.LocaleMode;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Locale;

import java.util.logging.Logger;

import es.deusto.spq.server.logger.ServerLogger;
import es.deusto.spq.server.remote.HotelManager;
import es.deusto.spq.server.remote.IHotelManager;


public class Server {

	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException {
		super();
	}

	public static void main(String[] args) {

		if (args.length != 3) {
			ServerLogger.getLogger().severe("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}


		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Logger log = ServerLogger.getLogger();
		
		String url = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IHotelManager hotelManager = new HotelManager();
			Naming.rebind(url, hotelManager);
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(System.in);
			java.io.BufferedReader stdin = new java.io.BufferedReader(inputStreamReader);
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
			log.info("Server active and waiting...");
		} catch (Exception e) {
			log.severe("RMI error. Turning off... - " + e.getMessage());
		}
	}

}
