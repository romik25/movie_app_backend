package com.cts.mba.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mba.constants.Constants;
import com.cts.mba.dao.Error;
import com.cts.mba.dao.JwtExpired;
import com.cts.mba.entities.Movie;
import com.cts.mba.exceptions.MovieNotFoundException;
import com.cts.mba.feignClient.FeignUtil;
import com.cts.mba.services.MovieService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/s2/movie")
public class MovieController {

	@Autowired
	private MovieService service;

	@Autowired
	private FeignUtil util;

	private Error error = new Error();

	@GetMapping("/{id}")
	public ResponseEntity<?> getMovie(@RequestHeader(name = "Authorization", required = false) String header,
			@PathVariable("id") int id) throws MovieNotFoundException {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Movie foundMovie = this.service.getMovieById(id);

		return new ResponseEntity<Movie>(foundMovie, HttpStatus.OK);

	}

	@GetMapping("/name/{name}")
	public ResponseEntity<?> getMovieByName(@RequestHeader(name = "Authorization", required = false) String header,
			@PathVariable("name") String name) throws MovieNotFoundException {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Movie foundMovie = this.service.getMovieByName(name);

		return new ResponseEntity<Movie>(foundMovie, HttpStatus.OK);

	}

	@GetMapping("/getMoviesByTheatre/{id}")
	public ResponseEntity<?> getMoviesByTheatreId(
			@RequestHeader(name = "Authorization", required = false) String header, @PathVariable("id") int id) {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		List<Movie> allMovies = service.getMovieByTheatreId(id);
		if (allMovies == null) {
			error.setError(Constants.THEATRE_NOT_FOUND);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<Movie>>(allMovies, HttpStatus.OK);

	}

	@GetMapping("/getAllMovies")
	public ResponseEntity<?> getMovies(@RequestHeader(name = "Authorization", required = false) String header) {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		List<Movie> allMovies = service.getAllMovies();

		return new ResponseEntity<List<Movie>>(allMovies, HttpStatus.OK);

	}

}
