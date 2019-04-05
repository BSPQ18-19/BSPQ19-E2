package es.deusto.spq.server.data.jdo;

import java.time.LocalDateTime;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Reservation {
	private String reservationId;
	private Hotel hotel;
	private Guest guest;
	private LocalDateTime reservationDate;
	private LocalDateTime arrivalDate;
	private LocalDateTime departureDate;
	private float price;
	
	public Reservation(String reservationId, Hotel hotel, Guest guest, LocalDateTime reservationDate, 
			LocalDateTime arrivalDate, LocalDateTime departureDate, float price) {
		super();
		this.reservationId = reservationId;
		this.reservationDate = reservationDate;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.price = price;
		this.guest = guest;
		this.hotel = hotel;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDateTime getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	public LocalDateTime getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDateTime arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setUser(Guest guest) {
		this.guest = guest;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}	
}