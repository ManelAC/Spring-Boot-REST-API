package com.movierecommendations.api.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Autowired
    private MovieConverter movieConverter;

    public MovieDTO saveMovie(MovieDTO movieDTO) {
        Movie movie = movieConverter.convertDTOToEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieConverter.convertEntityToDTO(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
	}

    public List<Movie> getMoviesWithTitle(String title) {
        return movieRepository.findByTitleAllIgnoreCaseContaining(title);
    }

    public MovieDTO addNewMovie(MovieDTO movieDTO) {
        if(movieDTO.getTitle() == null) {
            throw new IllegalStateException("Movie not created, title can not be null.");
        }
        if(movieDTO.getGenre() == null) {
            throw new IllegalStateException("Movie not created, genre can not be null.");
        }
        if(movieDTO.getDirector() == null) {
            throw new IllegalStateException("Movie not created, director can not be null.");
        }
        if(movieDTO.getCountry() == null) {
            throw new IllegalStateException("Movie not created, country can not be null.");
        }
        if(movieDTO.getReleaseYear() == null) {
            throw new IllegalStateException("Movie not created, release date can not be null.");
        }

        if(movieDTO.getTitle().length() < 1) {
            throw new IllegalStateException("Movie not created, title's length can't be 0.");
        }
        if(movieDTO.getGenre().length() < 1) {
            throw new IllegalStateException("Movie not created, genre's length can't be 0.");
        }
        if(movieDTO.getDirector().length() < 1) {
            throw new IllegalStateException("Movie not created, director's length can't be 0.");
        }
        if(movieDTO.getCountry().length() < 3) {
            throw new IllegalStateException("Movie not created, country's length can't be smaller than 2.");
        }
        if(movieDTO.getReleaseYear() < 1878) {
            throw new IllegalStateException("Movie not created, the earliest possible year for a movie is 1878.");
        }

        Optional<Movie> movieWithSameData = movieRepository.findByTitleAndGenreAndDirectorAndCountryAndReleaseYearAllIgnoreCase(movieDTO.getTitle(), movieDTO.getGenre(), movieDTO.getDirector(), movieDTO.getCountry(), movieDTO.getReleaseYear());

        if(movieWithSameData.isPresent()) {
            throw new IllegalStateException("Movie not created, a movie with the same information already exists.");
        }

        return saveMovie(movieDTO);
    }

    @Transactional
    public MovieDTO updateMovie(Long movieId, MovieDTO updatedMovieDTO) {
        Movie movieFromDatabase = movieRepository.findById(movieId).orElseThrow(() -> new IllegalStateException("No movie has the ID: " + movieId));

        if(updatedMovieDTO == null) {
            throw new IllegalStateException("Movie not updated, the updated movie object is null.");
        }

        Optional<Movie> movieWithSameData = movieRepository.findByTitleAndGenreAndDirectorAndCountryAndReleaseYearAllIgnoreCase(updatedMovieDTO.getTitle(), updatedMovieDTO.getGenre(), updatedMovieDTO.getDirector(), updatedMovieDTO.getCountry(), updatedMovieDTO.getReleaseYear());

        if(movieWithSameData.isPresent()) {
            throw new IllegalStateException("Movie not updated, a movie with the same information already exists.");
        }

        if(updatedMovieDTO.getCountry() != null && updatedMovieDTO.getCountry().length() == 1) {
            throw new IllegalStateException("Movie not updated, country's length can't be 1.");
        }
        if(updatedMovieDTO.getReleaseYear() != null && updatedMovieDTO.getReleaseYear() < 1878) {
            throw new IllegalStateException("Movie not updated, the earliest possible year for a movie is 1878.");
        }

        if(updatedMovieDTO.getTitle() != null && updatedMovieDTO.getTitle().length() > 0) {
            movieFromDatabase.setTitle(updatedMovieDTO.getTitle());
        }
        if(updatedMovieDTO.getGenre() != null && updatedMovieDTO.getGenre().length() > 0) {
            movieFromDatabase.setGenre(updatedMovieDTO.getGenre());
        }
        if(updatedMovieDTO.getDirector() != null && updatedMovieDTO.getDirector().length() > 0) {
            movieFromDatabase.setDirector(updatedMovieDTO.getDirector());
        }
        if(updatedMovieDTO.getCountry() != null && updatedMovieDTO.getCountry().length() >= 3) {
            movieFromDatabase.setCountry(updatedMovieDTO.getCountry());
        }
        if(updatedMovieDTO.getReleaseYear() != null && updatedMovieDTO.getReleaseYear() >= 1878) {
            movieFromDatabase.setReleaseYear(updatedMovieDTO.getReleaseYear());
        }
        
        Movie movieToReturn = movieRepository.save(movieFromDatabase);
        
        return movieConverter.convertEntityToDTO(movieToReturn);
    }

    public void deleteMovie(Long movieId) {
        if(movieRepository.existsById(movieId) == false) {
            throw new IllegalStateException("No movie has the ID: " + movieId);
        }

        movieRepository.deleteById(movieId);
    }
}
