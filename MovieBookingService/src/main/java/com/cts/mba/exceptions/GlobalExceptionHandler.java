package com.cts.mba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.mba.constants.Constants;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	 @ExceptionHandler(MovieNotFoundException.class)
	 public ResponseEntity<?> movieNotFound(){
		  
		  Error error  = new Error(Constants.MOVIE_NOT_FOUND);
		  return new ResponseEntity<Error>(error  , HttpStatus.NOT_FOUND);
		  
	 }
	 
	 
	 
	 
	 @ExceptionHandler(TheatreNotFoundException.class)
	 public ResponseEntity<?> theatreNotFound(){
		  
		  Error error  = new Error(Constants.THEATRE_NOT_FOUND);
		  return new ResponseEntity<Error>(error  , HttpStatus.NOT_FOUND);
		  
	 }
	 
	 
	 @ExceptionHandler(FeignException.class)
	 public ResponseEntity<?> feignException(FeignException e){
		  
		  Error error  = new Error("Token Expired");
		  return new ResponseEntity<Error>(error  , HttpStatus.BAD_REQUEST);
		  
	 }

}
