package com.cts.mba.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cts.mba.constants.Constants;
import com.cts.mba.dto.JwtTokenDAO;
import com.cts.mba.dto.LoginDAO;
import com.cts.mba.dto.Error;
import com.cts.mba.dto.JwtExpired;
import com.cts.mba.dto.Message;
import com.cts.mba.dto.UserDTO;
import com.cts.mba.entities.Movie;

import com.cts.mba.exceptions.MovieNotFoundException;
import com.cts.mba.feignClient.FeignUtil;
import com.cts.mba.services.MovieService;
import com.google.gson.Gson;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/s4/movie")
public class MovieController {

	@Autowired
	private MovieService service;

	@Autowired
	private RestTemplate template;

	private Error error = new Error();

	private Message message = new Message();

	private JwtTokenDAO token;

	private Gson gson = new Gson();

	@Autowired
	private FeignUtil util;


	@PostMapping("/{roleId}/register")
	public ResponseEntity<?> register(@RequestBody UserDTO user, @PathVariable("roleId") int roleId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(user, headers);
		try {
			String body = this.template.exchange("https://admin-movie-service-g4h0dgdvb9h2gjdf.centralus-01.azurewebsites.net/api/v1/auth/" + roleId + "/register/",
					HttpMethod.POST, entity, String.class).getBody();

			this.message = gson.fromJson(body, Message.class);
			return ResponseEntity.ok(this.message);
		} catch (HttpStatusCodeException exception) {

			switch (exception.getStatusCode().value()) {

			case 400:

				this.error = gson.fromJson(exception.getResponseBodyAsString(), Error.class);
				return new ResponseEntity<>(this.error, HttpStatus.BAD_REQUEST);

			default:

				this.error = gson.fromJson(exception.getResponseBodyAsString(), Error.class);
				return new ResponseEntity<>(this.error, HttpStatus.INTERNAL_SERVER_ERROR);

			}

		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody() LoginDAO login) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<LoginDAO> entity = new HttpEntity<LoginDAO>(login, headers);

		try {
			String body = this.template
					.exchange("https://admin-movie-service-g4h0dgdvb9h2gjdf.centralus-01.azurewebsites.net/api/v1/auth/login", HttpMethod.POST, entity, String.class)
					.getBody();

			this.token = gson.fromJson(body, JwtTokenDAO.class);
			return ResponseEntity.ok(this.token);
			
		} catch (HttpStatusCodeException exception) {

			switch (exception.getStatusCode().value()) {

			case 400:

				this.error = gson.fromJson(exception.getResponseBodyAsString(), Error.class);
				return new ResponseEntity<>(this.error, HttpStatus.BAD_REQUEST);

			default:

				this.error = gson.fromJson(exception.getResponseBodyAsString(), Error.class);
				return new ResponseEntity<>(this.error, HttpStatus.INTERNAL_SERVER_ERROR);

			}

		}

	}

	@PostMapping("/{id}/addMovie")
	public ResponseEntity<?> createMovie(@RequestBody Movie movie, @PathVariable("id") int theatreId,
			@RequestHeader(name = "Authorization", required = false) String header) {

		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Movie createdMovie = this.service.createMovie(movie, theatreId);

		if (createdMovie == null) {
			error.setError(Constants.THEATRE_NOT_FOUND);
			return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<Movie>(createdMovie, HttpStatus.OK);
	}

	@PutMapping("/updateMovie")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie,
			@RequestHeader(name = "Authorization", required = false) String header) throws MovieNotFoundException {

		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Movie updatedMovie = this.service.updateMovie(movie);
		return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);

	}

	@DeleteMapping("/deleteMovieById/{id}")
	public ResponseEntity<?> deleteMapping(@PathVariable("id") int id,
			@RequestHeader(name = "Authorization", required = false) String header) throws MovieNotFoundException {

		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		this.service.deleteMovie(id);

		Message message = new Message();
		message.setMessage(Constants.MOVIE_DELETED);
		return new ResponseEntity<Message>(message, HttpStatus.OK);

	}

}
