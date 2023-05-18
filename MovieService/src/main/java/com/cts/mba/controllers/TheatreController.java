package com.cts.mba.controllers;



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
import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.TheatreNotFoundException;
import com.cts.mba.feignClient.FeignUtil;
import com.cts.mba.services.TheatreService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v1/s2/theatre")
public class TheatreController {

	@Autowired
	private TheatreService service;

	@Autowired
	private FeignUtil util;

	private Error error = new Error();

	@GetMapping("/getTheatres")
	public ResponseEntity<?> getTheatres(@RequestHeader(name = "Authorization", required = false) String header) {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}


		return new ResponseEntity<>(this.service.getTheatres(), HttpStatus.OK); 

		

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTheatreById(@RequestHeader(name = "Authorization", required = false) String header,
			@PathVariable("id") int id) throws TheatreNotFoundException {

		if (header == null) {

			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}

		JwtExpired valid = this.util.validToken(header);
		if (valid.isExpired() == true) {
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}

		Theatre theatre = this.service.getTheatre(id);

		return new ResponseEntity<Theatre>(theatre, HttpStatus.OK);

	}

}
