package es.deusto.spq.server.data.dto;

import es.deusto.spq.server.data.jdo.*;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test suite for the Assembler class.
 * @author Iñigo Apellániz
 */
public class AssemblerTest {

    /**
     * Use the same timestamp across all tests to avoid unrealistic failures due to millisecond delays in testing.
     */
    private static Timestamp timestamp;
    private static Assembler assembler;
    
    @BeforeClass
    public static void initialize() {
    	assembler = new Assembler();
    }
    @Before
    public void setUp() throws Exception {
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Test that wen we pass a null Hotel to the assembleHotel method we get null in return
     */
    @Test
    public void assembleHotel_hotelNull_returnNull() {
        Assembler assembler = new Assembler();
        Assert.assertNull(assembler.assembleHotel(null));
    }

    /**
     * Test the assembleHotel method
     */
    @Test
    public void assembleHotel_Hotel_Assemble() {
        Hotel hotel = new Hotel("test",
                "Test Hotel",
                "Isle of France",
                timestamp,
                timestamp
        );

        HotelDTO hotelDTO = new HotelDTO("test",
                "Test Hotel",
                "Isle of France",
                timestamp,
                timestamp
        );

        Assert.assertEquals(hotelDTO, assembler.assembleHotel(hotel));
    }

    /**
     * Test the disassembleHotel method
     */
    @Test
    public void disassembleHotel_Hotel_Dissasemble() {
        Hotel hotel = new Hotel("test",
                "Test Hotel",
                "Isle of France",
                timestamp,
                timestamp
        );

        HotelDTO hotelDTO = new HotelDTO("test",
                "Test Hotel",
                "Isle of France",
                timestamp,
                timestamp
        );

        Assert.assertEquals(hotel, assembler.disassembleHotel(hotelDTO));

    }

    /**
     * Test the assembleRoom method
     */
    @Test
    public void assembleRoom_Room_Assemble() {
        Room room = new Room("roomtest", 26f, 42f, RoomType.SINGLE, false);
        RoomDTO roomDTO = new RoomDTO("roomtest", 26f, 42f, RoomType.SINGLE, false);

        Assert.assertEquals(roomDTO, assembler.assembleRoom(room));

    }


    /**
     * Test the disassembleRoom method
     */
    @Test
    public void disassembleRoom_Room_Disassemble() {
        final List<String> features = Arrays.asList("views", "jacuzzi", "tv");

        Room room = new Room("roomtest", 26f, 42f, RoomType.SINGLE, false);
        RoomDTO roomDTO = new RoomDTO("roomtest", 26f, 42f, RoomType.SINGLE, false);

        Assert.assertEquals(room, assembler.disassembleRoom(roomDTO));

    }

    /**
     * Test the assembleUser method
     */
    @Test
    public void assembleUser_Guest_Assemble() {
        Guest guest = new Guest(
                "testguest",
                "John Doe",
                "example@example.com",
                "hunter12",
                "11",
                "3, White Road St., LA, CA"
        );


        UserDTO userDTO = new UserDTO(
                "testguest",
                "John Doe",
                true
        );

        Assert.assertEquals(userDTO, assembler.assembleUser(guest));

    }

    /**
     * Test the disassembleUser method with a Guest
     */
    @Test
    public void disassembleUser_Guest_Disassemble() {
        Guest guest = new Guest(
                "testguest",
                "John Doe",
                "example@example.com",
                "hunter12",
                "11",
                "3, White Road St., LA, CA"
        );


        UserDTO userDTO = new UserDTO(
                "testguest",
                "John Doe",
                true
        );

        Assert.assertEquals(guest, assembler.disassembleUser(userDTO));

    }


    /**
     * Test the disassembleUser method with an Administrator
     */
    @Test
    public void disassembleUser_Admin_Disassemble() {
        Administrator administrator = new Administrator(
                "testadmin",
                "John Doe",
                "example@example.com",
                "hunter12",
                "3, White Road St., LA, CA"
        );


        UserDTO userDTO = new UserDTO(
                "testadmin",
                "John Doe",
                false
        );

        Assert.assertEquals(administrator, assembler.disassembleUser(userDTO));

    }

    /**
     * Test the assembleReview method
     */
    @Test
    public void assembleReview_Review_Assemble() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Hotel hotel = new Hotel("test",
                "Test Hotel",
                "Isle of France",
                timestamp,
                timestamp
        );

        Guest guest = new Guest(
                "testguest",
                "John Doe",
                "example@example.com",
                "hunter12",
                "11",
                "3, White Road St., LA, CA"
        );

        Review review = new Review(
                "testreview",
                "opinion here",
                7,
                timestamp,
                hotel,
                guest
        );


        ReviewDTO reviewDTO = new ReviewDTO(
                "testreview",
                "opinion here",
                7,
                timestamp,
                assembler.assembleHotel(hotel),
                assembler.assembleUser(guest)
        );

        Assert.assertEquals(reviewDTO, assembler.assembleReview(review));

    }


    /**
     * Test the disassembleReview method
     */
    @Test
    public void disassembleReview_Review_Disassemble() {
        Hotel hotel = new Hotel("test",
                "Test Hotel",
                "Isle of France",
                timestamp,
                timestamp
        );

        Guest guest = new Guest(
                "testguest",
                "John Doe",
                "example@example.com",
                "hunter12",
                "11",
                "3, White Road St., LA, CA"
        );

        Review review = new Review(
                "testreview",
                "opinion here",
                7,
                timestamp,
                hotel,
                guest
        );


        ReviewDTO reviewDTO = new ReviewDTO(
                "testreview",
                "opinion here",
                7,
                timestamp,
                assembler.assembleHotel(hotel),
                assembler.assembleUser(guest)
        );

        Assert.assertEquals(review, assembler.disassembleReview(reviewDTO));

    }

    @Test
    public void assembleReservationTest() {
    	Reservation reservation = new Reservation("RID", null, null, null, null);
    	ReservationDTO expectedAssembling = new ReservationDTO("RID", null, null, null, null);
    	ReservationDTO assemblingResult = assembler.assembleReservation(reservation);
    	Assert.assertEquals(expectedAssembling, assemblingResult);
    }
    
    @Test
    public void disassembleReservationTest() {
    	ReservationDTO reservation = new ReservationDTO("RID", null, null, null, null);
    	Reservation expectedDisassembling = new Reservation("RID", null, null, null, null);
    	Reservation disassemblingResult = assembler.disassembleReservation(reservation);
    	Assert.assertEquals(expectedDisassembling, disassemblingResult);
    }

}
