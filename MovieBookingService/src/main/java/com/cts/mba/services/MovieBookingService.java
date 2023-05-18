package com.cts.mba.services;

import java.util.List;
import java.util.Set;

import com.cts.mba.dto.TicketDTO;
import com.cts.mba.entities.Ticket;


public interface MovieBookingService {
	
	public Ticket booking(int movieId, TicketDTO ticket);
	
	 public Set<Ticket> getTicketsOfMovie(int id);
	 
	 public List<Ticket> getTicketsForUser(int id);

}
