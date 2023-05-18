package com.cts.mba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.cts.mba.services.AuthService;


@SpringBootApplication
public class UserAuthServiceApplication {
	
	 @Autowired AuthService service;

	public static void main(String[] args) {
		SpringApplication.run(UserAuthServiceApplication.class, args);
		 
//		AuthServiceImpl service  = new AuthServiceImpl();
//		
//		  service.populateRoles();
			
	      
		 
		 
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
           
		   service.populateRoles();	
		   
	}

}
