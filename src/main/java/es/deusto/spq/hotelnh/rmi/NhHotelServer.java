package es.deusto.spq.hotelnh.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import es.deusto.spq.hotelnh.data.NhHotel;
import es.deusto.spq.hotelnh.data.dto.NhHotelAssembler;
import es.deusto.spq.hotelnh.data.dto.NhHotelDTO;

public class NhHotelServer extends UnicastRemoteObject implements INhHotelServer {
	
	private Map<String, NhHotel> hotels = new TreeMap<String, NhHotel>();

	protected NhHotelServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		
		
//		hotels.put("H01", new NhHotel("H01", "Hotel1", "Bilbao", null, dateTime("2019-04-01"), dateTime("2019-06-30")));
//		hotels.put("H02", new NhHotel("H02", "Hotel2", "Barcelona", null, dateTime("2019-04-01"), dateTime("2019-08-31")));
//		hotels.put("H03", new NhHotel("H03", "Hotel3", "Madrid", null, dateTime("2019-01-01"), dateTime("2019-12-31")));
//		hotels.put("H04", new NhHotel("H04", "Hotel4", "Sevilla", null, dateTime("2019-06-01"), dateTime("2019-08-31")));
//		hotels.put("H05", new NhHotel("H05", "Hotel5", "Zaragoza", null, dateTime("2019-06-01"), dateTime("2019-08-31")));
//		hotels.put("H06", new NhHotel("H06", "Hotel6", "Gijon", null, dateTime("2019-01-01"), dateTime("2019-06-30")));
		
	}

	@Override
	public ArrayList<NhHotelDTO> retrieveHotels() throws RemoteException {
		
		ArrayList<NhHotelDTO> hotelsDTO = new ArrayList<>();
		NhHotelAssembler hotelAssembler = new NhHotelAssembler();
		
		System.out.println("Retrieving hotels...");
		for(NhHotel hotel: hotels.values()) {
			hotelsDTO.add(hotelAssembler.assemble(hotel));
		}
		
		if(hotelsDTO.isEmpty()) {
			System.out.println("New exception - There are no hotels for the requested information.");
			throw new RemoteException("NH HOTELS - There are no hotels for the requested information.");
		}
		return hotelsDTO;
	}

	public static void main(String[] args) {
		if (args.length != 3) {
            System.out.println("\"usage: java [policy] [codebase] iberia.rmi.server.NhHotelServer [host] [port] [server]\"");
            System.exit(1);
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        String serviceName = "//" + args[0] + ":" + args[1] + "/" + args[2];
        try {
        	INhHotelServer server = new NhHotelServer();
            Naming.rebind(serviceName, server);
            System.out.println("[*] Iberia Server '" + args[0] + "' listening at " + args[1] + ":" + args[2]);
        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

	}
}
