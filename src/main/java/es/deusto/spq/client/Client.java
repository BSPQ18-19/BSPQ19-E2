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
    
    public boolean createHotel(String id, String name, String location, String[] services, String seasonStart, String seasonEnd) {
    	
    	try {
    		System.out.println("Creating new hotel...");
			HotelDTO hotelDTO = serviceLocator.getService().createHotel(id, name, location, services, seasonStart, seasonEnd);
			if(hotelDTO!=null) {
				System.out.println("Hotel created successfully!");
				return true;
			}else {
				System.out.println("Hotel cannot be created.");
			}
		} catch (RemoteException e) {
			System.out.println("Error creating a new hotel: " + e.getMessage());
		}
    	return false;
    }
    
    public ArrayList<HotelDTO> retrieveHotels() {
    	System.out.println("Getting list of hotels.");
    	try {
			ArrayList<HotelDTO> hotel = serviceLocator.getService().retrieveHotels();
//			for(HotelDTO hotelDTO: hotel) {
//				System.out.println(hotelDTO.getLocation());
//			}
//			HotelDTO[] hotels = hotel.toArray(new HotelDTO[hotel.size()]);
			
			
			if(hotel != null && hotel.size() != 0) {
				System.out.println("List of hotels retrieved succesfully.");
				return hotel;
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