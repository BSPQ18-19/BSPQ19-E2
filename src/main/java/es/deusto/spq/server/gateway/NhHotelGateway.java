package es.deusto.spq.server.gateway;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.hotelnh.data.dto.NhHotelDTO;
import es.deusto.spq.hotelnh.rmi.INhHotelServer;
import es.deusto.spq.server.data.Hotel;

public class NhHotelGateway implements INhHotelGateway {

	private INhHotelServer service;
		
	public NhHotelGateway(String IP, String port, String name) {
		
		String nameAdmin = "//" + IP + ":" + port + "/" + name;
    	if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}    	
    	try {
    		service = (INhHotelServer) java.rmi.Naming.lookup(nameAdmin);
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
	
	@Override
	public ArrayList<Hotel> retrieveHotels() throws RemoteException {
		try {
			ArrayList<Hotel> hotels = new ArrayList<>();
			ArrayList<NhHotelDTO> nhHotels = service.retrieveHotels();
			
			for(NhHotelDTO nhHotelDTO: nhHotels) {
				hotels.add(new Hotel(nhHotelDTO.getHotelId(), nhHotelDTO.getName(), 
						nhHotelDTO.getLocation(), nhHotelDTO.getServices(), 
						nhHotelDTO.getSeasonStart(), nhHotelDTO.getSeasonEnding()));
			}
			return hotels;
		}catch (RemoteException e) {
			System.out.println("Error from NH Hotel server: " + e.getMessage());
			throw e;
		}

	}

}
