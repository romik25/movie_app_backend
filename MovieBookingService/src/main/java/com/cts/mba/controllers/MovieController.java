package com.cts.mba.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mba.constants.Constants;
import com.cts.mba.dto.Error;
import com.cts.mba.dto.JwtExpired;
import com.cts.mba.dto.TicketDTO;

import com.cts.mba.entities.Ticket;
import com.cts.mba.feignClient.FeignUtil;
import com.cts.mba.services.MovieBookingService;

@RestController
@RequestMapping("/api/v1/s3/booking")
public class MovieController {

	@Autowired
	private MovieBookingService service;

	@Autowired
	private FeignUtil util;

	private Error error = new Error();

	@PostMapping("/bookTickets/{id}")
	public ResponseEntity<?> createBooking(@RequestHeader(name = "Authorization", required = false) String header,
			@RequestBody TicketDTO ticket, @PathVariable("id") int id) {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Ticket booking = this.service.booking(id, ticket);
		if (booking == null) {
			error.setError("Movie with the given id does not exist/ Seats Not Available");
			return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Ticket>(booking, HttpStatus.OK);
	}

	@GetMapping("/movie/tickets/{id}")
	public ResponseEntity<?> getTicketsOfAMovie(@RequestHeader(name = "Authorization", required = false) String header,
			@PathVariable("id") int id) {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Set<Ticket> ticketsOfMovie = this.service.getTicketsOfMovie(id);
		if (ticketsOfMovie == null) {
			error.setError("Movie with the given id does not exist");
			return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		}

		List<Ticket> collected = ticketsOfMovie.stream().map(ticket -> ticket).collect(Collectors.toList());

		return new ResponseEntity<List<Ticket>>(collected, HttpStatus.OK);
	}

}
