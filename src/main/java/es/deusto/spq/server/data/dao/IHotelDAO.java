package es.deusto.spq.server.data.dao;

import java.util.List;

import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;

public interface IHotelDAO {
	
	public List<HotelDTO> getHotels(UserDTO authorization);
	public HotelDTO getHotelbyID(UserDTO authorization, String hotelID);
	public HotelDTO createHotel(UserDTO authorization, HotelDTO hotel);
	public boolean deleteHotel(UserDTO authorization, String hotelID);
	public HotelDTO editHotel(UserDTO authorization, String hotelID, HotelDTO updates);
}