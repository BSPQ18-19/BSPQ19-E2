package es.deusto.spq.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import es.deusto.spq.server.data.Hotel;
import es.deusto.spq.server.data.Room;
import es.deusto.spq.server.data.RoomStatus;
import es.deusto.spq.server.data.RoomType;
import es.deusto.spq.server.data.dao.HotelDAO;
import es.deusto.spq.server.data.dao.IHotelDAO;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.gateway.INhHotelGateway;
import es.deusto.spq.server.gateway.NhHotelGateway;

public class Server extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;
	
//	private Map<String, Hotel> hotels = new TreeMap<String, Hotel>();
//	private INhHotelGateway gateway;
	private IHotelDAO dao;
	
	protected Server() throws RemoteException {
		super();

		
		
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
		// TODO Auto-generated method stub
		return null;
	}
}
