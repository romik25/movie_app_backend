package com.cts.mba.repos;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.mba.entities.Movie;





@Transactional
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	public Optional<Movie>findById(int id);
	
	public Optional<Movie> findByMovieName(String name);

} 
