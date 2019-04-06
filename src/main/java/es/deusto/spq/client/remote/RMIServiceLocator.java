package es.deusto.spq.client.remote;

import java.util.logging.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.remote.IHotelManager;

public class RMIServiceLocator {

	private static RMIServiceLocator rsl = null;
	private IHotelManager hotelManager;
	private Logger log;
	
	static {
		rsl = new RMIServiceLocator();
	}
	
	private RMIServiceLocator() {
		log = ClientLogger.getLogger();
	}
	
	public static RMIServiceLocator getServiceLocator() {
		return rsl;
	}
	
	public void setService(String ip, int port, String serviceName) {
		String url = "//" + ip + ":" + port + "/" + serviceName;
		try {
			hotelManager = (IHotelManager) java.rmi.Naming.lookup(url);
			log.info("Connected to: " + url);
		} catch (Exception e) {
			log.severe("Exception stablishing connection to: " + url);
		}
	}
	
	public IHotelManager getHotelManager() {
		return hotelManager;
	}
}
