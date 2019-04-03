package es.deusto.spq.server.data.dto;

import es.deusto.spq.server.data.Hotel;

public class HotelAssembler {

	public HotelDTO assemble(Hotel hotel) {
		if (hotel == null)
			return null;
		HotelDTO hotelDTO = new HotelDTO(hotel.getHotelId(), hotel.getName(), hotel.getLocation(),
				hotel.getServices(), hotel.getSeasonStart(), hotel.getSeasonEnding());
		System.out.println("Assembling hotel...");
		return hotelDTO;
	}
}
