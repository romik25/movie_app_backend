package com.cts.mba.services;

import com.cts.mba.dao.JwtExpired;
import com.cts.mba.dao.JwtTokenDAO;
import com.cts.mba.dao.LoginDAO;
import com.cts.mba.entities.User;

public interface AuthService {
	
	
	   public JwtTokenDAO login(LoginDAO login);
	  
	   public User register(User user , int roleId);
	   
	   public User findUserByEmail(String email);
	   
	   public User findUserById(int id);
	   
	   public JwtExpired tokenExpired(String header);
	   
	   public Boolean  passwordMatches(User user , LoginDAO login);
	   
	   public void populateRoles();

}
