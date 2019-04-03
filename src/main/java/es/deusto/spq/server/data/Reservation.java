package es.deusto.spq.server.data;

import java.time.LocalDateTime;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public class Reservation {
	private String reservationId;
	private LocalDateTime reservationDate, arrivalDate, departureDate;
	private int price;
	private User user;
	private Hotel hotel;
	
	public Reservation(String reservationId, LocalDateTime reservationDate, LocalDateTime arrivalDate,
			LocalDateTime departureDate, int price, User user, Hotel hotel) {
		super();
		this.reservationId = reservationId;
		this.reservationDate = reservationDate;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.price = price;
		this.user = user;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}	
}
