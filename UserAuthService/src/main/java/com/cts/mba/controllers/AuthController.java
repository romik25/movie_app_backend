package com.cts.mba.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mba.constants.Constants;
import com.cts.mba.dao.ErrorDAO;
import com.cts.mba.dao.JwtExpired;
import com.cts.mba.dao.JwtTokenDAO;
import com.cts.mba.dao.LoginDAO;
import com.cts.mba.dao.MessageDAO;
import com.cts.mba.dao.UserDAO;
import com.cts.mba.entities.Role;
import com.cts.mba.entities.User;
import com.cts.mba.services.AuthService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	
	 @Autowired
	 private AuthService service;
	 
	 
	 
 
	 private ErrorDAO error  = new ErrorDAO();
	 
	 private MessageDAO message = new MessageDAO();
	 
	 
	 @PostMapping("/{roleId}/register")
	 public ResponseEntity<?> register(@RequestBody User user , @PathVariable("roleId") int roleId){
		  
		    
		 User newUser = this.service.register(user , roleId);
		      if(newUser == null) {
		    	  error.setError(Constants.USER_EXIST);
		    	  return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		      }
		    
		      message.setMessage(Constants.USER_REGISTERED);
		      
		      return ResponseEntity.ok(message);
		  
		 
	 }
	 
	 
		@PostMapping("/login")
		public ResponseEntity<?> login(@RequestBody() LoginDAO login) throws Exception {

			 User user = this.service.findUserByEmail(login.getUsername());

			if (user == null) {
				error.setError(Constants.USER_NOT_REGISTERED);
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}

			java.util.Set<Role> roles = user.getRoles();
			if (roles.size() == 0) {
				error.setError(Constants.USER_NOT_REGISTERED);
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
			
			  if(!(this.service.passwordMatches(user , login))) {
				  error.setError(Constants.WRONG_PASSWORD);
				  return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			  }
			 

			JwtTokenDAO token = this.service.login(login);

			return new ResponseEntity<>(token, HttpStatus.OK);
		}
		
		
		
		
		@GetMapping("/userById/{id}")
		public ResponseEntity<?> getUserFromId(@RequestHeader(name = "Authorization", required = false) String header , @PathVariable("id") int id){
			      
			if (header == null) {

				error.setError(Constants.UNAUTHORIZED);
				return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
			}

			JwtExpired valid = this.service.tokenExpired(header);
			if (valid.isExpired() == true) {
				return new ResponseEntity<>(valid, HttpStatus.UNAUTHORIZED);
			}
			
			  User user = this.service.findUserById(id);
			  if(user == null) {
				  error.setError(Constants.USER_NOT_FOUND);
				  return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			  }
			     UserDAO dao  = new UserDAO(user.getFirstName() , user.getLastName() , user.getContact() , user.getEmail());
			   return ResponseEntity.ok(dao);
		}
		
		
		
		
		@GetMapping("/token/expired")
		public JwtExpired tokenExpired(@RequestHeader(name = "auth", required = false) String header) {

			if (header == null) {
				return null;
			}

			JwtExpired expired = this.service.tokenExpired(header);

			return expired;
		}

}
