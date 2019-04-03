package es.deusto.spq.server.gateway;

import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.server.data.Hotel;

public interface INhHotelGateway {

	public ArrayList<Hotel> retrieveHotels() throws RemoteException;
}
