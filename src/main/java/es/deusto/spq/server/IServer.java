package es.deusto.spq.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.server.data.dto.HotelDTO;

public interface IServer extends Remote {
	
	void sayMessage(String message) throws RemoteException;
	void sayHello() throws RemoteException;
	
	public ArrayList<HotelDTO> retrieveHotels() throws RemoteException;
}