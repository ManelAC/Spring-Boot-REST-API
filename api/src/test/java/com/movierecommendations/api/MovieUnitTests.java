package com.movierecommendations.api;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.movierecommendations.api.movie.Movie;
import com.movierecommendations.api.movie.MovieRepository;
import com.movierecommendations.api.movie.MovieService;

@ExtendWith(MockitoExtension.class)
public class MovieUnitTests {
    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    void movieHasRightAttributes() {
        Long movieId = 1L;
        String title = "The Test Movie";
        String genre = "Action";
        String director = "John";
        String country = "New Zealand";
        Integer releaseYear = 2023;

        Movie movieConstructor = new Movie(movieId, title, genre, director, country, releaseYear);
        Movie movieSetter = new Movie();

        movieSetter.setMovieId(movieId);
        movieSetter.setTitle(title);
        movieSetter.setGenre(genre);
        movieSetter.setDirector(director);
        movieSetter.setCountry(country);
        movieSetter.setReleaseYear(releaseYear);

        Mockito.when(movieRepository.save(any(Movie.class))).then(AdditionalAnswers.returnsFirstArg());

        Movie testMovie = movieRepository.save(movieSetter);

        Assertions.assertEquals(testMovie.getMovieId(), movieConstructor.getMovieId());
        Assertions.assertEquals(testMovie.getTitle(), movieConstructor.getTitle());
        Assertions.assertEquals(testMovie.getGenre(), movieConstructor.getGenre());
        Assertions.assertEquals(testMovie.getDirector(), movieConstructor.getDirector());
        Assertions.assertEquals(testMovie.getCountry(), movieConstructor.getCountry());
        Assertions.assertEquals(testMovie.getReleaseYear(), movieConstructor.getReleaseYear());
    }
}
