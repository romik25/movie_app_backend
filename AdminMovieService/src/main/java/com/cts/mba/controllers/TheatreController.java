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
import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.TheatreNotFoundException;
import com.cts.mba.feignClient.FeignUtil;
import com.cts.mba.services.TheatreService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v1/s4/theatre")
public class TheatreController {
	
	
	@Autowired
	private TheatreService service;
	
	private Error error  = new Error();
	
	@Autowired
	private FeignUtil util;
	
	
	
	@PostMapping("/addTheatre")
	public  ResponseEntity<?> addTheatre(@RequestBody Theatre theatre , @RequestHeader(name = "Authorization", required = false) String header){
		
		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}
		
		JwtExpired valid = this.util.validToken(header);
		if(valid.isExpired() == true){
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}
		     
		Theatre theatre1 = this.service.addTheatre(theatre);
		return new ResponseEntity<Theatre>(theatre1 , HttpStatus.OK);
	}
	
	
	@PutMapping("/updateTheatre")
	public ResponseEntity<?> updateTheatre(@RequestBody Theatre theatre , @RequestHeader(name = "Authorization", required = false) String header) throws TheatreNotFoundException{
		
		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}
		
		JwtExpired valid = this.util.validToken(header);
		if(valid.isExpired() == true){
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}
		     
		  Theatre updateTheatre = this.service.updateTheatre(theatre);
		  return new ResponseEntity<Theatre>(updateTheatre , HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTheatre(@PathVariable("id") int id , @RequestHeader(name = "Authorization", required = false) String header) throws TheatreNotFoundException{
		
		if (header == null) {
			error.setError(Constants.UNAUTHORIZED);
			return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
		}
		
		JwtExpired valid = this.util.validToken(header);
		if(valid.isExpired() == true){
			return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
		}
		
		   String removeTheatre = this.service.removeTheatre(id);
		   if(removeTheatre != null) {
			   Message m = new Message();
			   m.setMessage(removeTheatre);
			   return new ResponseEntity<Message>(m , HttpStatus.OK);
		   }
		   
		   Error e  = new Error(Constants.THEATRE_NOT_FOUND);
		   return new ResponseEntity<Error>(e , HttpStatus.OK);
		   
	}

}
