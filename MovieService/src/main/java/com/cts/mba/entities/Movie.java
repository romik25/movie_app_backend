package com.cts.mba.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Movie {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String movieName;
	private int seatsAvailable;
	private String theatreName;
	private String description;
	private String genre;
	private String status;
	private double ticketPrice;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "theatre_id", referencedColumnName = "id")
	private Theatre theatre;
	
	
	
	  @OneToMany(mappedBy="movie"  , cascade = CascadeType.ALL , orphanRemoval = true)
	  @JsonIgnore
	  private Set<Ticket> tickets;
	
	
	public Movie() {
		
	}
	
	


	public Movie(int id , String name , int seats , String theatreName) {
		 this.id = id;
		 this.movieName  =name;
		 this.seatsAvailable  =seats;
		 this.theatreName = theatreName;
		 
		 
		 
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMovieName() {
		return movieName;
	}


	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}


	public int getSeatsAvailable() {
		return seatsAvailable;
	}


	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}


	public String getTheatreName() {
		return theatreName;
	}


	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	
	
	public Theatre getTheatre() {
		return theatre;
	}


	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public Set<Ticket> getTickets() {
		return tickets;
	}


	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getGenre() {
		return genre;
	}




	public void setGenre(String genre) {
		this.genre = genre;
	}




	public double getTicketPrice() {
		return ticketPrice;
	}




	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	
	

}
