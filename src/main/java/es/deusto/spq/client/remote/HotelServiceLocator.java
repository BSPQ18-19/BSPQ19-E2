package es.deusto.spq.client.remote;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import es.deusto.spq.server.IServer;

public class HotelServiceLocator {
	private IServer service;
	
	public HotelServiceLocator() {
		
	}
	
	public IServer getService() {
		return service;
	}
	
    public void setService(String[] args) {
    	String nameAdmin = "//" + args[0] + ":" + args[1] + "/" + args[2];
    	if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}    	
    	try {
    		service = (IServer) java.rmi.Naming.lookup(nameAdmin);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
