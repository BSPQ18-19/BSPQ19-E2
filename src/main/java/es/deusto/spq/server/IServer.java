package es.deusto.spq.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;

import es.deusto.spq.server.data.dto.HotelDTO;

public interface IServer extends Remote {
	
	void sayMessage(String message) throws RemoteException;
	void sayHello() throws RemoteException;
	
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException;
	public HotelDTO createHotel(String id, String name, String location, String[] services, String seasonStart, String seasonEnd) throws RemoteException;
}