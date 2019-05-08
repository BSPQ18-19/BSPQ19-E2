package es.deusto.spq.server.data.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ReservationDTOTest {

	public ReservationDTO reservation;
	private static String id;
	private static String guestId;
	private static List<RoomDTO> rooms;
	
	@BeforeClass
	public static void initialize() {
		id = "RID";
		guestId = "GID";
		rooms = new ArrayList<RoomDTO>();
	}
	
	@Before
	public void setUp() {
		reservation = new ReservationDTO(id, guestId, rooms);
	}
	
	@Test
	public void shortConstructorTest() {
		reservation = new ReservationDTO(id, guestId);
	}
	
	@Test
	public void getSetIdTest() {
		Assert.assertEquals(id, reservation.getId());
		String newId = id + "new";
		reservation.setId(newId);
		Assert.assertEquals(newId, reservation.getId());
	}
	
	@Test
	public void getSetGuestIdTest() {
		Assert.assertEquals(guestId, reservation.getGuestId());
		String newId = guestId + "new";
		reservation.setGuestId(newId);
		Assert.assertEquals(newId, reservation.getGuestId());
	}

	@Test
	public void getSetRoomsTest() {
		Assert.assertEquals(rooms, reservation.getRooms());
		reservation.setRooms(null);
		Assert.assertEquals(null, reservation.getRooms());
	}

}
