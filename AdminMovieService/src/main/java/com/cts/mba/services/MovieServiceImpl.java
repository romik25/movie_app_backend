package com.cts.mba.services;

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
	public Movie createMovie(Movie movie, int theatreId) {

		Optional<Theatre> theatre = this.repo2.findById(theatreId);

		if (theatre.isEmpty()) {
			return null;
		}

		Theatre theatre1 = theatre.get();

		movie.setTheatre(theatre1);
		movie.setTheatreName(theatre1.getTheatreName());
		movie.setSeatsAvailable(theatre1.getTotalSeats());

		Movie saved = this.repo.saveAndFlush(movie);
		return saved;
	}

	@Override
	public Movie updateMovie(Movie movie) throws MovieNotFoundException {

		Optional<Movie> found = this.repo.findById(movie.getId());
		if (found.isPresent()) {
			Movie movie2 = found.get();
			movie2.setMovieName(movie.getMovieName());
			movie2.setSeatsAvailable(movie.getSeatsAvailable());
			movie2.setDescription(movie.getDescription());
			movie2.setGenre(movie.getGenre());
			Movie saved = this.repo.saveAndFlush(movie2);
			return saved;
		} else {
			throw new MovieNotFoundException();
		}

	}

	@Override
	public void deleteMovie(int id) throws MovieNotFoundException {

		Optional<Movie> found = this.repo.findById(id);

		if (found.isPresent()) {
			this.repo.deleteById(id);
		} else {
			throw new MovieNotFoundException();
		}
	}

}
