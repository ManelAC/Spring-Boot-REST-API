package com.movierecommendations.api.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleAllIgnoreCaseContaining(String title);

    Optional<Movie> findByTitleAndGenreAndDirectorAndCountryAndReleaseYearAllIgnoreCase(String title, String genre, String director, String country, Integer releaseYear);
}
