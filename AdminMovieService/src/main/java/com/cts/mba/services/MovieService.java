package com.cts.mba.services;





import com.cts.mba.entities.Movie;
import com.cts.mba.exceptions.MovieNotFoundException;


public interface MovieService {
	
	
	  public Movie createMovie(Movie movie , int theatreId);
	
	  
	  public Movie updateMovie(Movie movie) throws MovieNotFoundException;
	  
	  public void deleteMovie(int id) throws MovieNotFoundException ;

}
