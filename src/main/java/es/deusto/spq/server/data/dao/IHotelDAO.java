package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.Hotel;

public interface IHotelDAO {
	public void storeHotel(Hotel hotel);
	public Hotel getHotel(String hotelID);
	public List<Hotel> getHotels();
	public void cleanDB();
}