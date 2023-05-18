package com.cts.mba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.mba.dao.JwtExpired;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	
	 @ExceptionHandler(ExpiredJwtException.class)
	 public ResponseEntity<?> tokenExpired(ExpiredJwtException ex){
		 
		    JwtExpired expired   = new JwtExpired();
		    expired.setExpired(true);
		    
		    return new ResponseEntity<JwtExpired>(expired, HttpStatus.BAD_REQUEST);
		  
	 }

}
