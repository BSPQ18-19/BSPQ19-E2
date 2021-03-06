package es.deusto.spq.client.remote;

import org.apache.log4j.Logger;

import es.deusto.spq.client.logger.ClientLogger;
import es.deusto.spq.server.remote.IHotelManager;

public class RMIServiceLocator {

	private static RMIServiceLocator rsl = new RMIServiceLocator();
	private IHotelManager hotelManager = null;
	private Logger log;
	
	private RMIServiceLocator() {
		log = ClientLogger.getLogger();
		log.info("RMIServiceLocator initialized");
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
			log.fatal("Exception stablishing connection to: " + url);
		}
	}
	
	public IHotelManager getHotelManager() {
		return hotelManager;
	}
}