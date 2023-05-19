package com.cts.mba.services;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.mba.constants.Constants;
import com.cts.mba.dao.JwtExpired;
import com.cts.mba.dao.JwtTokenDAO;
import com.cts.mba.dao.LoginDAO;
import com.cts.mba.entities.Role;
import com.cts.mba.entities.User;
import com.cts.mba.repositories.RoleRepository;
import com.cts.mba.repositories.UserRepository;
import com.cts.mba.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private AuthenticationManager auth;

	@Autowired
	private CustomUserDetailsService service;

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	

	@Override
	public User register(User user, int roleId) {

		// TODO Auto-generated method stub
		User found = this.repo.findByEmail(user.getEmail());
		if (found == null) {
			Role role = this.roleRepo.findById(roleId).get();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.addRole(role);
			User saved = this.repo.save(user);
			logger.info("User Created " + saved);
			return saved;

		}

		return null;
	}

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		User userFound = this.repo.findByEmail(email);

		if (userFound == null) {
			return null;
		}

		return userFound;
	}

	@Override
	public Boolean passwordMatches(User user, LoginDAO login) {
		// TODO Auto-generated method stub

		boolean matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
		System.out.println(matches);
		return matches;

	}

	@Override
	public JwtTokenDAO login(LoginDAO login) {
		// TODO Auto-generated method stub

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUsername(),
				login.getPassword());

		auth.authenticate(token);

		UserDetails user = this.service.loadUserByUsername(login.getUsername());

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		String tokenString = this.jwtutil.generateToken(user);

		JwtTokenDAO tkn = new JwtTokenDAO(tokenString);
		  
		tkn.setId(this.findUserByEmail(user.getUsername()).getId());

		for (GrantedAuthority auth : authorities) {

			if (auth.getAuthority().equals(Constants.ROLE_ADMIN)) {
				tkn.setType(Constants.ROLE_ADMIN);
			} else {
				tkn.setType(Constants.ROLE_USER);
			}

		}

		logger.info("Login Successfull by " + tkn.getType());

		return tkn;
	}
	
	

	@Override
	public JwtExpired tokenExpired(String header) {

		// TODO Auto-generated method stub

		JwtExpired jwtExpired = new JwtExpired();

		String token = header.substring(7);

		Boolean expired = this.jwtutil.expired(token);
		if (expired) {

			jwtExpired.setExpired(true);
			return jwtExpired;
		}

		jwtExpired.setExpired(false);

		logger.info("Token Expired -> " + jwtExpired.isExpired());
		return jwtExpired;
	}
	
	

	@Override
	public void populateRoles() {
		// TODO Auto-generated method stub

		logger.info("Populating Roles ... ");

		Role role = new Role();

		Role role1 = this.roleRepo.findByName(Constants.ROLE_ADMIN);

		if (role1 == null) {
			role.setName(Constants.ROLE_ADMIN);
			roleRepo.save(role);
		}

		Role role2 = this.roleRepo.findByName(Constants.ROLE_USER);

		if (role2 == null) {
			role.setName(Constants.ROLE_USER);
			roleRepo.save(role);
		}

	}

	@Override
	public User findUserById(int id) {
		  Optional<User> userFound = this.repo.findById(id);
		  if(userFound.isEmpty()) {
			  return null;
		  }
		  
		return userFound.get();
	}

	@Override
	public User changePassword(String newPassword, String email) {
		// TODO Auto-generated method stub
		 
		 User user = this.repo.findByEmail(email);
		 if(user == null)
			  return null;
		 
		 user.setPassword(passwordEncoder.encode(newPassword));
		 
		return this.repo.save(user);
		 
	}

}
