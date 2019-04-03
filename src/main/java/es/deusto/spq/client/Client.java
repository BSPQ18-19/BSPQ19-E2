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
    
	public ArrayList<HotelDTO> getCurrentHotels() {
		return currentHotels;
	}
	public void setCurrentHotels(HotelDTO hotelDTO) {
		this.currentHotels.add(hotelDTO);
	}
	public void setCurrentHotels() {
		this.currentHotels.clear();
	}
	
	public static void main(String[] args) throws RemoteException{
		new Client(args);
	}
	
}