package es.deusto.spq.server.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import es.deusto.spq.server.data.Hotel;

public class HotelDaoTest {

	
	public static void main(String[] args) {
		ArrayList<Hotel> hotels = new ArrayList<>();

		String str1 = "2019-04-01";
		String str2 = "2019-06-30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime1 = LocalDate.parse(str1, formatter);
		LocalDate dateTime2 = LocalDate.parse(str2, formatter);
		
		Hotel hotel1 = new Hotel("H01", "Hotel1", "Bilbao", new String[]{"one", "two"}, dateTime1, dateTime2);
		Hotel hotel2 = new Hotel("H02", "Hotel2", "Madrid", new String[]{"three", "four"}, dateTime1, dateTime2);
		
		hotels.add(hotel1);
		hotels.add(hotel2);
		
		HotelDAO dao = new HotelDAO();
		for(Hotel hotel: hotels) {
			dao.storeHotel(hotel);
		}
		
		Hotel hotelAux = dao.getHotel(hotel1.getHotelId());
		System.out.println(hotelAux.getName().equals(hotel1.getName()));
	}
}
