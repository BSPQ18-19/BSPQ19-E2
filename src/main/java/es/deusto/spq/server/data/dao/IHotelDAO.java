package es.deusto.spq.server.data.dao;

import java.util.ArrayList;

import es.deusto.spq.server.data.jdo.Hotel;

public interface IHotelDAO {

	public Hotel storeHotel(Hotel hotel);
	public Hotel getHotel(String hotelID);
	public ArrayList<Hotel> getHotels();
	public boolean deleteHotel(String hotelID);
	public void cleanDB();
}
