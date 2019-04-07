package es.deusto.spq.client;

import java.util.logging.Logger;

import es.deusto.spq.client.gui.Login;
import es.deusto.spq.client.gui.RegisterWindow;
import es.deusto.spq.client.controller.*;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.IServer;
import es.deusto.spq.server.locale.LocaleManager;

import java.util.Locale;

public class Client {

	public static void showSignup() {
		new RegisterWindow(HotelManagementController.getController());
	}

	public static void main(String[] args) {

		HotelManagementController controller = HotelManagementController.getController();
		new Login(controller);
		
		Logger log = ClientLogger.getLogger();

		if (args.length != 3) {
			log.severe("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}


		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IServer serverStub = (IServer) java.rmi.Naming.lookup(name);
			serverStub.sayHello();
			LocaleManager.setLocale(new Locale("es", "ES"));
			serverStub.sayMessage(LocaleManager.getMessage("test.bye"));
		} catch (Exception e) {
			log.severe("RMI error. Turning down the client... - " + e.getMessage());
//			e.printStackTrace();
		}
	}
}