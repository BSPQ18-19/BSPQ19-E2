package es.deusto.spq.server.data;

import java.time.LocalDateTime;

public class Review {
	private User user;
	private Hotel hotel;
	private LocalDateTime date, replyDate;
	private String comment, reply;
	private int stars;
}
