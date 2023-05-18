package com.cts.mba.services;

import java.util.List;

import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.TheatreNotFoundException;

public interface TheatreService {
	
	
	public Theatre getTheatre(int id) throws TheatreNotFoundException ;
	
	public List<Theatre> getTheatres();
	 
	
	 
	 

}
