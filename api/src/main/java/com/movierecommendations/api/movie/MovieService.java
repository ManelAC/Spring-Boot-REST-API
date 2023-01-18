package com.movierecommendations.api.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
	}

    public List<Movie> getMoviesWithTitle(String title) {
        return movieRepository.findByTitleAllIgnoreCaseContaining(title);
    }

    public Movie addNewMovie(Movie movie) {
        if(movie.getTitle() == null) {
            throw new IllegalStateException("Movie not created, title can not be null.");
        }
        if(movie.getGenre() == null) {
            throw new IllegalStateException("Movie not created, genre can not be null.");
        }
        if(movie.getDirector() == null) {
            throw new IllegalStateException("Movie not created, director can not be null.");
        }
        if(movie.getCountry() == null) {
            throw new IllegalStateException("Movie not created, country can not be null.");
        }
        if(movie.getReleaseYear() == null) {
            throw new IllegalStateException("Movie not created, release date can not be null.");
        }

        if(movie.getTitle().length() < 1) {
            throw new IllegalStateException("Movie not created, title's length can't be 0.");
        }
        if(movie.getGenre().length() < 1) {
            throw new IllegalStateException("Movie not created, genre's length can't be 0.");
        }
        if(movie.getDirector().length() < 1) {
            throw new IllegalStateException("Movie not created, director's length can't be 0.");
        }
        if(movie.getCountry().length() < 3) {
            throw new IllegalStateException("Movie not created, country's length can't be smaller than 2.");
        }
        if(movie.getReleaseYear() < 1878) {
            throw new IllegalStateException("Movie not created, the earliest possible year for a movie is 1878.");
        }

        Optional<Movie> movieWithSameData = movieRepository.findByTitleAndGenreAndDirectorAndCountryAndReleaseYearAllIgnoreCase(movie.getTitle(), movie.getGenre(), movie.getDirector(), movie.getCountry(), movie.getReleaseYear());

        if(movieWithSameData.isPresent()) {
            throw new IllegalStateException("Movie not created, a movie with the same information already exists.");
        }

        return movieRepository.save(movie);
    }

    @Transactional
    public Movie updateMovie(Long movieId, Movie updatedMovie) {
        Movie movieFromDatabase = movieRepository.findById(movieId).orElseThrow(() -> new IllegalStateException("No movie has the ID: " + movieId));

        if(updatedMovie == null) {
            throw new IllegalStateException("Movie not updated, the updated movie object is null.");
        }

        Optional<Movie> movieWithSameData = movieRepository.findByTitleAndGenreAndDirectorAndCountryAndReleaseYearAllIgnoreCase(updatedMovie.getTitle(), updatedMovie.getGenre(), updatedMovie.getDirector(), updatedMovie.getCountry(), updatedMovie.getReleaseYear());

        if(movieWithSameData.isPresent()) {
            throw new IllegalStateException("Movie not updated, a movie with the same information already exists.");
        }

        if(updatedMovie.getCountry() != null && updatedMovie.getCountry().length() == 1) {
            throw new IllegalStateException("Movie not updated, country's length can't be 1.");
        }
        if(updatedMovie.getReleaseYear() != null && updatedMovie.getReleaseYear() < 1878) {
            throw new IllegalStateException("Movie not updated, the earliest possible year for a movie is 1878.");
        }

        if(updatedMovie.getTitle() != null && updatedMovie.getTitle().length() > 0) {
            movieFromDatabase.setTitle(updatedMovie.getTitle());
        }
        if(updatedMovie.getGenre() != null && updatedMovie.getGenre().length() > 0) {
            movieFromDatabase.setGenre(updatedMovie.getGenre());
        }
        if(updatedMovie.getDirector() != null && updatedMovie.getDirector().length() > 0) {
            movieFromDatabase.setDirector(updatedMovie.getDirector());
        }
        if(updatedMovie.getCountry() != null && updatedMovie.getCountry().length() >= 3) {
            movieFromDatabase.setCountry(updatedMovie.getCountry());
        }
        if(updatedMovie.getReleaseYear() != null && updatedMovie.getReleaseYear() >= 1878) {
            movieFromDatabase.setReleaseYear(updatedMovie.getReleaseYear());
        }

        return movieRepository.save(movieFromDatabase);
    }

    public void deleteMovie(Long movieId) {
        if(movieRepository.existsById(movieId) == false) {
            throw new IllegalStateException("No movie has the ID: " + movieId);
        }

        movieRepository.deleteById(movieId);
    }
}
