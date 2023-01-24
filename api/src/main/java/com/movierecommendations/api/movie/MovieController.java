package com.movierecommendations.api.movie;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Returns a list of all the movies.")
    @GetMapping("/movies")
	public List<Movie> getAllMovies() {
		return movieService.getAllMovies();
	}

    @Operation(summary = "Returns the the list of movies that contain the specified title.")
    @GetMapping("/movies/{title}")
	public List<Movie> getSpecificMovieId(@Valid @PathVariable("title") @Parameter(description = "Title of the movie. Not case sensitive.") String title) {
		return movieService.getMoviesWithTitle(title);
	}

    @Operation(summary = "Adds a new movie into the database based on the information provided.")
    @PostMapping("/movies")
    public MovieDTO newMovie(@Valid @RequestBody @Parameter(description = "Data of the new movie.") MovieDTO movieDTO) {
        return movieService.addNewMovie(movieDTO);
    }

    @Operation(summary = "Updates the values of the corresponding movie.")
    @PutMapping("/movies/{id}")
    public MovieDTO updateMovie(@Valid @PathVariable("id") @Parameter(description = "Id of the movie to be updated.") Long id, 
        @Valid @RequestBody @Parameter(description = "Updated data of the movie.") MovieDTO movieDTO) {
        return movieService.updateMovie(id, movieDTO);
    }

    @Operation(summary = "Deletes the corresponding movie from the database.")
    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@Valid @PathVariable("id") @Parameter(description = "Id of the movie to be deleted.") Long id) {
        movieService.deleteMovie(id);
    }
}
