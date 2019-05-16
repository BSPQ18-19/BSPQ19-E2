package es.deusto.spq.server.data.dto;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ReservationDTOTest {

	public ReservationDTO reservation;
	private static String id;
	private static String guestId;
	private static String roomId;
	
	@BeforeClass
	public static void initialize() {
		id = "RID";
		guestId = "GID";
		roomId = null;
	}
	
	@Before
	public void setUp() {
		reservation = new ReservationDTO(id, guestId, null);
	}
	
	@Test
	public void shortConstructorTest() {
		reservation = new ReservationDTO(id, guestId, null);
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
		Assert.assertEquals(roomId, reservation.getRoomId());
		reservation.setRoomId("R03");
		Assert.assertEquals("R03", reservation.getRoomId());
	}

}
