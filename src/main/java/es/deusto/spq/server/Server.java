package es.deusto.spq.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.deusto.spq.server.data.Hotel;
import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.IHotelDAO;
import es.deusto.spq.server.data.dto.HotelAssembler;
import es.deusto.spq.server.data.dto.HotelDTO;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Hotel> hotels = new TreeMap<String, Hotel>();
//	private INhHotelGateway gateway;
	private IHotelDAO dao;
	
	protected Server() throws RemoteException {
		super();

		hotels.put("H01", new Hotel("H01", "Hotel1", "Bilbao", null, LocalDate.of(2019, 4, 01), LocalDate.of(2019, 6, 30)));
		hotels.put("H02", new Hotel("H02", "Hotel2", "Barcelona", null, LocalDate.of(2019, 4, 01), LocalDate.of(2019, 8, 31)));
		hotels.put("H03", new Hotel("H03", "Hotel3", "Madrid", null, LocalDate.of(2019, 1, 01), LocalDate.of(2019, 12, 31)));
		hotels.put("H04", new Hotel("H04", "Hotel4", "Sevilla", null, LocalDate.of(2019, 6, 01), LocalDate.of(2019, 8, 31)));
		hotels.put("H05", new Hotel("H05", "Hotel5", "Zaragoza", null, LocalDate.of(2019, 6, 01), LocalDate.of(2019, 8, 31)));
		hotels.put("H06", new Hotel("H06", "Hotel6", "Gijon", null, LocalDate.of(2019, 1, 01), LocalDate.of(2019, 6, 30)));
	
		
		
	}
	

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("How to invoke: java [policy] [codebase] Server.Server [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String url = "//" + args[0] + ":" + args[1] + "/" + args[2];

		try {
			IServer objServer = new Server();
			Naming.rebind(url, objServer);
			System.out.println("Server '" + url + "' active and waiting...");
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
		} catch (Exception e) {
			System.err.println("Hello exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void sayMessage(String message) throws RemoteException {
		System.out.println(message);
	}

	@Override
	public void sayHello() throws RemoteException {
		System.out.println("Hello!");		
	}


	@Override
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException {
		ArrayList<HotelDTO> hotelsDTO = new ArrayList<>();
		HotelAssembler hotelAssembler = new HotelAssembler();
		
		System.out.println("Retrieving hotels...");
		for(Hotel hotel: hotels.values()) {
			hotelsDTO.add(hotelAssembler.assemble(hotel));
		}
		
		if(hotelsDTO.isEmpty()) {
			System.out.println("New exception - There are no hotels for the requested information.");
			throw new RemoteException("HOTELS - There are no hotels for the requested information.");
		}
		return hotelsDTO;
	}
	
}
