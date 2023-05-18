package com.cts.mba.services;



import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.TheatreNotFoundException;

public interface TheatreService {
	
	
	
	
	 public Theatre addTheatre(Theatre theatre);
	
	 
	 public Theatre  updateTheatre(Theatre theatre) throws TheatreNotFoundException ;
	 
	 public  String removeTheatre(int id) throws TheatreNotFoundException ;
	 
	 
	 

}
