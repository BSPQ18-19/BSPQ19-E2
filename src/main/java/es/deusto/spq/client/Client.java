package es.deusto.spq.client;

import es.deusto.spq.client.controller.*;
import es.deusto.spq.client.gui.ClientWindow;
import es.deusto.spq.client.gui.Login;
import es.deusto.spq.client.gui.RegisterWindow;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.controller.*;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.server.locale.LocaleManager;

import java.util.Locale;

import org.apache.log4j.Logger;

public class Client {

	public static void showSignup() {
		new RegisterWindow(HotelManagementController.getController());
	}

	public static void main(String[] args) {

		Logger log = ClientLogger.getLogger();

		if (args.length != 3) {
			log.fatal("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		Client client = new Client();
		client.initializeClient(args[0], Integer.parseInt(args[1]), args[2]);
		log.info("Client initialization finished");
		new Login(client.controller);
		
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
