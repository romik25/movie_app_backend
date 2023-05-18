package com.cts.mba.controllers;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
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

import com.cts.mba.constants.Constants;
import com.cts.mba.dto.Error;
import com.cts.mba.dto.JwtExpired;
import com.cts.mba.dto.Message;
import com.cts.mba.entities.Movie;
import com.cts.mba.exceptions.MovieNotFoundException;
import com.cts.mba.feignClient.FeignUtil;
import com.cts.mba.kafka.NotificationProducer;
import com.cts.mba.services.MovieService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v1/s4/movie")
public class MovieController {

	@Autowired
	private MovieService service;
	
	
	private Error error  = new Error();
	
	
	@Autowired
	private FeignUtil util;
	
	
	// Kafka Producer
	@Autowired
	private NotificationProducer producer;

	@PostMapping("/{id}/addMovie")
	public ResponseEntity<?> createMovie(@RequestBody Movie movie , @PathVariable("id") int theatreId ,@RequestHeader(name = "Authorization", required = false) String header) {
		
		
		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}
		
		JwtExpired valid = this.util.validToken(header);
		if(valid.isExpired() == true){
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}
		

		Movie createdMovie = this.service.createMovie(movie , theatreId);
		 
		  if(createdMovie == null) {
			   error.setError(Constants.THEATRE_NOT_FOUND);
			   return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
		  }
		
		  producer.sendNotificationMovie(createdMovie);

		return new ResponseEntity<Movie>(createdMovie, HttpStatus.OK);
	}

	@PutMapping("/updateMovie")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie  ,@RequestHeader(name = "Authorization", required = false) String header) throws MovieNotFoundException {
		
		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}
		
		JwtExpired valid = this.util.validToken(header);
		if(valid.isExpired() == true){
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Movie updatedMovie = this.service.updateMovie(movie);
		 producer.sendNotificationMovie(updatedMovie);
		return new ResponseEntity<Movie>(updatedMovie, HttpStatus.OK);

	}

	@DeleteMapping("/deleteMovieById/{id}")
	public ResponseEntity<?> deleteMapping(@PathVariable("id") int id , @RequestHeader(name = "Authorization", required = false) String header) throws MovieNotFoundException {
		
		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}
		
		JwtExpired valid = this.util.validToken(header);
		if(valid.isExpired() == true){
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		this.service.deleteMovie(id);

		Message message = new Message();
		message.setMessage(Constants.MOVIE_DELETED);
		return new ResponseEntity<Message>(message, HttpStatus.OK);

	}

}
