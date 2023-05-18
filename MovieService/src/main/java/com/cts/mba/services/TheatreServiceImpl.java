package com.cts.mba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.TheatreNotFoundException;
import com.cts.mba.repos.TheatreRepository;

@Service
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreRepository repo;



	@Override
	public Theatre getTheatre(int id) throws TheatreNotFoundException {

		Optional<Theatre> found = this.repo.findById(id);
		if (found.isPresent()) {
			return found.get();
		}

		throw new TheatreNotFoundException();
	}
	
	
	
	

	@Override
	public List<Theatre> getTheatres() {

		return repo.findAll();
	}



}
