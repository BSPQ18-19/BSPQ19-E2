package es.deusto.spq.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.client.gui.ClientWindow;
import es.deusto.spq.client.remote.HotelServiceLocator;
import es.deusto.spq.server.IServer;
import es.deusto.spq.server.data.dto.HotelDTO;

public class Client {

	private HotelServiceLocator serviceLocator;
	private ArrayList<HotelDTO> currentHotels;
	
    public Client(String args[]) throws RemoteException {
		super();
		serviceLocator = new HotelServiceLocator();
		serviceLocator.setService(args);
		this.currentHotels = new ArrayList<>();
		ClientWindow.getClientWindow(this).setVisible(true);
	}
    
    public HotelDTO[] retrieveHotels() {
    	System.out.println("Getting list of hotels.");
    	try {
			ArrayList<HotelDTO> hotel = serviceLocator.getService().retrieveHotels();
			HotelDTO[] hotels = hotel.toArray(new HotelDTO[hotel.size()]);
			if(hotels != null && hotels.length != 0) {
				System.out.println("List of hotels retrieved succesfully.");
			}else {
				System.out.println("Could not retrieve list of hotels");
			}
    	} catch (RemoteException e) {
			System.out.println("Error getting list of hotels: " + e.getMessage());
		}
		return null;
    }
    
	public static void main(String[] args) throws RemoteException{
		if (args.length != 3) {
			System.out.println("Use: java [policy] [codebase] Client.Client [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
			IServer serverStub = (IServer) java.rmi.Naming.lookup(name);
			serverStub.sayHello();
			serverStub.sayMessage("Bye!");
			new Client(args);
		} catch (Exception e) {
			System.err.println("RMI Example exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public ArrayList<HotelDTO> getCurrentHotels() {
		return currentHotels;
	}
	public void setCurrentHotels(HotelDTO hotelDTO) {
		this.currentHotels.add(hotelDTO);
	}
	public void setCurrentHotels() {
		this.currentHotels.clear();
	}
	
}