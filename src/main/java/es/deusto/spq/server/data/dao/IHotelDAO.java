package es.deusto.spq.server.data.dao;

import java.util.ArrayList;

import es.deusto.spq.server.data.jdo.Hotel;

public interface IHotelDAO {

	void storeHotel(Hotel hotel);
	Hotel getHotel(String hotelID);
	ArrayList<Hotel> getHotels();
	boolean deleteHotel(String hotelID);
	void cleanDB();
}
