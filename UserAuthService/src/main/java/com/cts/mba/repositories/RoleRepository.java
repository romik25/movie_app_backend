package com.cts.mba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.mba.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
      
	  Role findByName(String name);
	  
	  
}
