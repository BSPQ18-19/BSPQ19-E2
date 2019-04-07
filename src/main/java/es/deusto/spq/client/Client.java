package es.deusto.spq.client;

import java.util.logging.Logger;

import es.deusto.spq.client.controller.*;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.server.locale.LocaleManager;

import java.util.Locale;

public class Client {

	public static void main(String[] args) {

		Logger log = ClientLogger.getLogger();

		if (args.length != 3) {
			log.severe("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Client client = new Client();
		client.initializeClient(args[0], Integer.parseInt(args[1]), args[2]);
	}
	
	private HotelManagementController controller = null;
	private RMIServiceLocator rsl = null;
	
	public void initializeClient(String ip, int port, String serviceName) {
		controller = HotelManagementController.getController();
		rsl = RMIServiceLocator.getServiceLocator();
		rsl.setService(ip, port, serviceName);
		LocaleManager.setLocale(new Locale("es", "ES"));
	}

	public HotelManagementController getController() {
		return controller;
	}

	public RMIServiceLocator getRMIServiceLocator() {
		return rsl;
	}
	
	
}