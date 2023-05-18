package com.cts.mba.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mba.constants.Constants;
import com.cts.mba.entities.Theatre;
import com.cts.mba.exceptions.TheatreNotFoundException;
import com.cts.mba.repos.TheatreRepository;

@Service
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreRepository repo;

	@Override
	public Theatre addTheatre(Theatre theatre) {

		return this.repo.saveAndFlush(theatre);
	}

	@Override
	public Theatre updateTheatre(Theatre theatre) throws TheatreNotFoundException {

		Optional<Theatre> t = this.repo.findById(theatre.getId());
		if (t.isPresent()) {
			Theatre theatre2 = t.get();
			theatre2.setTheatreName(theatre.getTheatreName());
			return this.repo.saveAndFlush(theatre2);
		}
		throw new TheatreNotFoundException();
	}
	
	

	@Override
	public String removeTheatre(int id) throws TheatreNotFoundException {

		Optional<Theatre> t = this.repo.findById(id);
		if (t.isPresent()) {
			this.repo.deleteById(id);
			return Constants.THEATRE_DELETED;
		}

		throw new TheatreNotFoundException();
	}

}
