package com.cts.mba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.mba.constants.Constants;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	 @ExceptionHandler(MovieNotFoundException.class)
	 public ResponseEntity<?> movieNotFound(){
		  
		  Error error  = new Error(Constants.MOVIE_NOT_FOUND);
		  return buildResponseEntity(error);
	 }
	 
	 
	   @ExceptionHandler(FeignException.class)
	   protected ResponseEntity<Object> handleEntityNotFound(
			   FeignException e) {
		   Error error  = new Error("Token Expired");
	       return buildResponseEntity(error);
	   }
	   
	  
	 
	 
	 @ExceptionHandler(TheatreNotFoundException.class)
	 public ResponseEntity<?> theatreNotFound(){
		  
		  Error error  = new Error(Constants.THEATRE_NOT_FOUND);
		 return buildResponseEntity(error);
	 }
	 
	 
	 private ResponseEntity<Object> buildResponseEntity(Error error) {
	       return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
	   }
	 
	 


}
