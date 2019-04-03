package es.deusto.spq.hotelnh.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import es.deusto.spq.hotelnh.data.dto.NhHotelDTO;

public interface INhHotelServer extends Remote{
	public ArrayList<NhHotelDTO> retrieveHotels() throws RemoteException;
}
