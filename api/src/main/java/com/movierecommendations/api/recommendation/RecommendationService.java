package com.movierecommendations.api.recommendation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

import com.movierecommendations.api.movie.Movie;
import com.movierecommendations.api.movie.MovieRepository;
import com.movierecommendations.api.user.User;
import com.movierecommendations.api.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
//@EnableJpaRepositories(basePackages = {"com.movierecommendations.api.user", "com.movierecommendations.api.movie"})
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RecommendationService(RecommendationRepository recommendationRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
	}

    public List<Recommendation> getAllRecommendationsFromUser(Long userId) {
        return recommendationRepository.findByUserUserId(userId);
    }

    public List<Recommendation> getAllRecommendationsForMovie(Long movieId) {
        return recommendationRepository.findByMovieMovieId(movieId);
    }

    public Recommendation addNewRecommendation(Recommendation recommendation) {
        User userFromDatabase = userRepository.findById(recommendation.getUser().getUserId()).orElseThrow(() -> new IllegalStateException("No user has the ID: " + recommendation.getUser().getUserId()));

        Movie movieFromDatabase = movieRepository.findById(recommendation.getMovie().getMovieId()).orElseThrow(() -> new IllegalStateException("No movie has the ID: " + recommendation.getMovie().getMovieId()));

        if(recommendation.getScore() == null) {
            throw new IllegalStateException("Recommendation not created, score can not be null.");
        }
        else if(recommendation.getScore() < 0) {
            throw new IllegalStateException("Recommendation not created, score can't be lower than 0.");
        }
        else if(recommendation.getScore() > 10) {
            throw new IllegalStateException("Recommendation not created, score can't be higher than 10.");
        }

        recommendation.setUser(userFromDatabase);
        recommendation.setMovie(movieFromDatabase);

        recommendation.setRecommendationDate(LocalDate.now());

        return recommendationRepository.save(recommendation);
    }

    @Transactional
    public Recommendation updateRecommendation(Long recommendationId, Recommendation updatedRecommendation) {
        Recommendation recommendationFromDatabase = recommendationRepository.findById(recommendationId).orElseThrow(() -> new IllegalStateException("No recommendation has the ID: " + recommendationId));

        User userFromDatabase = userRepository.findById(updatedRecommendation.getUser().getUserId()).orElseThrow(() -> new IllegalStateException("No user has the ID: " + updatedRecommendation.getUser().getUserId()));

        Movie movieFromDatabase = movieRepository.findById(updatedRecommendation.getMovie().getMovieId()).orElseThrow(() -> new IllegalStateException("No movie has the ID: " + updatedRecommendation.getMovie().getMovieId()));

        if(updatedRecommendation.getScore() == null) {
            throw new IllegalStateException("Recommendation not created, score can not be null.");
        }
        else if(updatedRecommendation.getScore() < 0) {
            throw new IllegalStateException("Recommendation not created, score can't be lower than 0.");
        }
        else if(updatedRecommendation.getScore() > 10) {
            throw new IllegalStateException("Recommendation not created, score can't be higher than 10.");
        }

        recommendationFromDatabase.setUser(userFromDatabase);
        recommendationFromDatabase.setMovie(movieFromDatabase);

        return recommendationRepository.save(recommendationFromDatabase);
    }

    public void deleteRecommendation(Long recommendationId) {
        if(recommendationRepository.existsById(recommendationId) == false) {
            throw new IllegalStateException("No recommendation has the ID: " + recommendationId);
        }

        recommendationRepository.deleteById(recommendationId);
    }
    
}
