package es.deusto.spq.client;

import es.deusto.spq.client.controller.*;
import es.deusto.spq.client.gui.base.ViewFactory;
import es.deusto.spq.client.gui.base.ViewManager;
import es.deusto.spq.client.gui.base.ViewType;
import es.deusto.spq.client.gui.locale.LocaleMode;
import es.deusto.spq.client.gui.views.reservations.ReservationListView;
import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.client.remote.RMIServiceLocator;
import es.deusto.spq.client.gui.locale.LocaleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import es.deusto.spq.server.data.dto.ReservationDTO;
import es.deusto.spq.server.data.jdo.Reservation;
import org.apache.log4j.Logger;

public class Client {

	private ViewManager viewManager;

	/**
	 * Manages all localized strings
	 */
	private LocaleManager localeManager = new LocaleManager(this);

	public ViewManager getViewManager() {
		return viewManager;
	}

	/**
	 * Get the current active LocaleManager for this Client
	 * @return the LocaleManager for the Client
	 */
	public LocaleManager getLocaleManager() {
		return localeManager;
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
		localeManager.setMode(LocaleMode.DEBUG);
	}

	public HotelManagementController getController() {
		return controller;
	}

	public RMIServiceLocator getRMIServiceLocator() {
		return rsl;
	}
	
}
