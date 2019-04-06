package es.deusto.spq.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.DateFormatter;

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
	
	@SuppressWarnings("deprecation")
	protected Server() throws RemoteException {
		super();
		LocalDate localDate = LocalDate.of(2019, 04, 01);
	
		hotels.put("H01", new Hotel("H01", "Hotel1", "Bilbao", null, Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotels.put("H02", new Hotel("H02", "Hotel2", "Barcelona", null, Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotels.put("H03", new Hotel("H03", "Hotel3", "Madrid", null, Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotels.put("H04", new Hotel("H04", "Hotel4", "Sevilla", null, Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotels.put("H05", new Hotel("H05", "Hotel5", "Zaragoza", null, Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		hotels.put("H06", new Hotel("H06", "Hotel6", "Gijon", null, Timestamp.valueOf(localDate.atStartOfDay()), Timestamp.valueOf(localDate.atStartOfDay())));
		
		this.dao = new HotelDAO();
		dao.cleanDB();
		for(Hotel hotel: hotels.values()) {
			dao.storeHotel(hotel);
		}

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
		ArrayList<Hotel> listHotels = dao.getHotels();
		System.out.println(" --> SERVER:");
		System.out.println("ID: " + listHotels.get(1).getHotelId());
		System.out.println("NAME: " + listHotels.get(1).getName());
		System.out.println("LOCATION: " + listHotels.get(1).getLocation());
		for(Hotel hotel : listHotels) {
			hotelsDTO.add(hotelAssembler.assemble(hotel));
		}
		System.out.println(hotelsDTO.size());
		
		if(hotelsDTO.isEmpty()) {
			System.out.println("New exception - There are no hotels for the requested information.");
			throw new RemoteException("HOTELS - There are no hotels for the requested information.");
		}

		return hotelsDTO;
	}


	@Override
	public HotelDTO createHotel(String id, String name, String location, String[] services, String seasonStart,
			String seasonEnd) throws RemoteException {
		if(hotels.containsKey(id.trim()))
			throw new RemoteException("Server - Hotel ID aready exists");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		LocalDate localDateStart = LocalDate.parse(seasonStart.trim(), formatter);
		LocalDate localDateEnding = LocalDate.parse(seasonEnd.trim(), formatter);
		
		Hotel hotel = new Hotel(id.trim(), name.trim(), location.trim(), services,
				Timestamp.valueOf(localDateStart.atStartOfDay()), Timestamp.valueOf(localDateEnding.atStartOfDay()));
		hotels.put(hotel.getHotelId(), hotel);
		dao.storeHotel(hotel);
		
		HotelAssembler hotelAssembler = new HotelAssembler();
		return hotelAssembler.assemble(hotel);
	}


	@Override
	public boolean deleteHotel(String id) throws RemoteException {
		hotels.remove(id);
		dao.deleteHotel(id);
		if(hotels.containsKey(id) == false) {
			return true;
		}else {
			return false;
		}
	}
	
}
