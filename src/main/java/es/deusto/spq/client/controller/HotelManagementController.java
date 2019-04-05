package es.deusto.spq.client.controller;

import es.deusto.spq.client.remote.RMIServiceLocator;

public class HotelManagementController {

	private static HotelManagementController controller;
	private RMIServiceLocator rsl;
		
	private HotelManagementController() {
		rsl = RMIServiceLocator.getServiceLocator();
	}
	
	//TODO all the functionality's methods of the client
	
}
