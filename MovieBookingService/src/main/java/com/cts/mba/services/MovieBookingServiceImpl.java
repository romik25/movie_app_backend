package com.cts.mba.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mba.dto.TicketDTO;
import com.cts.mba.entities.Movie;
import com.cts.mba.entities.Ticket;
import com.cts.mba.repos.MovieRepository;
import com.cts.mba.repos.TicketRepository;

@Service
public class MovieBookingServiceImpl implements MovieBookingService {

	@Autowired
	private TicketRepository repo;

	@Autowired
	private MovieRepository repo2;

	@Override
	public Ticket booking(int movieId, TicketDTO ticketdto) {

		Optional<Movie> found = this.repo2.findById(movieId);

		if (found.isEmpty()) {
			return null;
		}

		Movie movie = found.get();

		if (movie.getSeatsAvailable() < ticketdto.getBookedSeats()) {
			return null;
		}

		int availableSeats = movie.getSeatsAvailable() - ticketdto.getBookedSeats();

		if (availableSeats == 0) {
			movie.setStatus("Unavailable");
		}

		int percentage = (availableSeats * 100)/movie.getTheatre().getTotalSeats() ;
		if (percentage <= 20) {
			movie.setStatus("Filling");
		}
		movie.setSeatsAvailable(availableSeats);

		Ticket ticket = new Ticket(ticketdto.getTotalSeats(), availableSeats, ticketdto.getBookedSeats(),
				ticketdto.getSeatNumber(), movie, movie.getMovieName(), movie.getTheatreName(), ticketdto.getUserId());

		ticket.setTotalSeats(movie.getTheatre().getTotalSeats());
		ticket.setBookingDate(ticketdto.getBookingDate());
		ticket.setPrice(ticketdto.getPrice());

		repo2.save(movie);

		Ticket save = this.repo.save(ticket);

		return save;

	}

	@Override
	public Set<Ticket> getTicketsOfMovie(int id) {
		// TODO Auto-generated method stub
		Optional<Movie> found = this.repo2.findById(id);
		if (found.isEmpty()) {
			return null;
		}

		Set<Ticket> tickets = found.get().getTickets();
		return tickets;
	}
	
	

	@Override
	public List<Ticket> getTicketsForUser(int id) {
		// TODO Auto-generated method stub
		  List<Ticket> ticketsByUser = this.repo.findByUserId(id);
		return ticketsByUser;
	}

}
