package com.cts.mba.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int totalSeats;
	private int availableSeats;
	private int bookedSeats;
	private String movieName;
	private String theatreName;
	private String bookingDate;
	private String seatNumber;
	private Double price;
	
	@ManyToOne(fetch = FetchType.EAGER  , cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	
	public Ticket() {
		
	}

	public Ticket(int totalSeats, int availableSeats, int bookedSeats, String seatNumber, Movie movie , String movieName , String theatreName  , int userId) {
		super();
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		this.bookedSeats = bookedSeats;
		this.seatNumber = seatNumber;
		this.movie = movie;
		this.movieName = movieName;
		this.theatreName = theatreName;
		this.userId = userId;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	public int getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}



	public String getTheatreName() {
		return theatreName;
	}



	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}



	public String getMovieName() {
		return movieName;
	}



	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	

}
