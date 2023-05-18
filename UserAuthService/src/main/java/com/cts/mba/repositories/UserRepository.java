package com.cts.mba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cts.mba.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	   @Query(value="Select * from user where email =:email" , nativeQuery = true)
	   User findByEmail(@Param("email") String email);

}
