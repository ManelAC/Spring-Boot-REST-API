package com.movierecommendations.api.recommendation;

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
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Returns a list of all the recommendations.")
    @GetMapping("/recommendations")
	public List<Recommendation> getAllRecommendations() {
		return recommendationService.getAllRecommendations();
	}

    @Operation(summary = "Returns all the recommendations from a specific user.")
    @GetMapping("/recommendations/user/{userid}")
	public List<Recommendation> getAllRecommendationsFromUser(@Valid @PathVariable("userid") @Parameter(description = "User ID.") Long userId) {
		return recommendationService.getAllRecommendationsFromUser(userId);
	}

    @Operation(summary = "Returns all the recommendations for a specific movie.")
    @GetMapping("/recommendations/movie/{movieid}")
	public List<Recommendation> getAllRecommendationsForMovie(@Valid @PathVariable("movieid") @Parameter(description = "Movie ID.") Long movieId) {
		return recommendationService.getAllRecommendationsForMovie(movieId);
	}

    @Operation(summary = "Adds a new recommendation into the database based on the information provided.")
    @PostMapping("/recommendations")
    public Recommendation newRecommendation(@Valid @RequestBody @Parameter(description = "Data of the new recommendation.") Recommendation recommendation) {
        return recommendationService.addNewRecommendation(recommendation);
    }

    @Operation(summary = "Updates the values of the corresponding recommendation.")
    @PutMapping("/recommendations/{id}")
    public Recommendation updateRecommendation(@Valid @PathVariable("id") @Parameter(description = "Id of the recommendation to be updated.") Long id, 
        @Valid @RequestBody @Parameter(description = "Updated data of the recommendation.") Recommendation recommendation) {
        return recommendationService.updateRecommendation(id, recommendation);
    }

    @Operation(summary = "Deletes the corresponding recommendation from the database.")
    @DeleteMapping("/recommendations/{id}")
    public void deleteRecommendation(@Valid @PathVariable("id") @Parameter(description = "Id of the recommendation to be deleted.") Long id) {
        recommendationService.deleteRecommendation(id);
    }
}
