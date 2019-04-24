package es.deusto.spq.client;

import es.deusto.spq.client.controller.*;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.locale.AllowedLocale;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.client.gui.locale.LocaleManager;

import java.util.Locale;

import org.apache.log4j.Logger;

public class Client {

	private ViewManager viewManager;

	public ViewManager getViewManager() {
		return viewManager;
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
	}
	
	private HotelManagementController controller = null;
	private RMIServiceLocator rsl = null;
	
	public void initializeClient(String ip, int port, String serviceName) {
		controller = HotelManagementController.getController();
		rsl = RMIServiceLocator.getServiceLocator();
		rsl.setService(ip, port, serviceName);

		viewManager = new ViewManager(this);
		viewManager.initialize();
		viewManager.openView(ViewFactory.buildView(ViewType.LOGIN, viewManager));
	}

	public HotelManagementController getController() {
		return controller;
	}

	public RMIServiceLocator getRMIServiceLocator() {
		return rsl;
	}
	
}
