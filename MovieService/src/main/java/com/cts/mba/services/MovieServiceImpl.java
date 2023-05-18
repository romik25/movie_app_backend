package com.cts.mba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mba.entities.Movie;
import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.MovieNotFoundException;
import com.cts.mba.repos.MovieRepository;
import com.cts.mba.repos.TheatreRepository;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository repo;
	
	@Autowired
	private TheatreRepository repo2;



	@Override
	public List<Movie> getAllMovies() {

		return this.repo.findAll();
	}

	@Override
	public Movie getMovieById(int id) throws MovieNotFoundException {

		Optional<Movie> found = this.repo.findById(id);

		if (found.isPresent()) {
			return found.get();
		} else {
			throw new MovieNotFoundException();
		}

	}

	@Override
	public Movie getMovieByName(String name) throws MovieNotFoundException {

		Optional<Movie> found = this.repo.findByMovieName(name);

		if (found.isPresent()) {
			return found.get();
		} else {
			throw new MovieNotFoundException();
		}
	}

	@Override
	public List<Movie> getMovieByTheatreId(int theatreId) {
		// TODO Auto-generated method stub
		  Optional<Theatre> findById = repo2.findById(theatreId);
		  if(findById.isEmpty()) {
			  return null;
		  }
		  
		return this.repo.findByTheatreId(theatreId);
		  
	}
	
	
	

	
	
	



}
