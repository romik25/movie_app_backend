package com.cts.mba.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.mba.entities.Role;
import com.cts.mba.entities.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	 @Autowired
	 private AuthService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		  
		     User userFound= this.service.findUserByEmail(username);
		     if(userFound == null) {
		    	 throw new UsernameNotFoundException("The user with the email " + username+" does not exist");
		     }
		     
		     Set<Role> roles = userFound.getRoles();
			  
			  List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
			   for(Role r : roles){
				   list.add(new SimpleGrantedAuthority(r.getName()));
			   }
		     
		  UserDetails user  = new org.springframework.security.core.userdetails.User(userFound.getEmail(), userFound.getPassword(), list);    
		  
		return user;
	}

}
