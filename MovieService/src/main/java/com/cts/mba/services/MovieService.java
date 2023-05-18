package com.cts.mba.services;

import java.util.List;



import com.cts.mba.entities.Movie;
import com.cts.mba.exceptions.MovieNotFoundException;


public interface MovieService {
	
	
	
	
	  public List<Movie> getAllMovies();
	  
	  public Movie getMovieById(int id) throws MovieNotFoundException;
	  
	  public Movie getMovieByName(String name) throws MovieNotFoundException;
	  
	  public List<Movie> getMovieByTheatreId(int theatreId);
	  

}
