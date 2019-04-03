package es.deusto.spq.hotelnh.data.dto;

import es.deusto.spq.hotelnh.data.NhHotel;

public class NhHotelAssembler {

	public NhHotelDTO assemble(NhHotel hotel) {
		if (hotel == null)
			return null;
		NhHotelDTO nhHotelDTO = new NhHotelDTO(hotel.getHotelId(), hotel.getName(), hotel.getLocation(),
				hotel.getServices(), hotel.getSeasonStart(), hotel.getSeasonEnding()); 
		System.out.println("Assembling hotel...");
		return nhHotelDTO;
	}
}
