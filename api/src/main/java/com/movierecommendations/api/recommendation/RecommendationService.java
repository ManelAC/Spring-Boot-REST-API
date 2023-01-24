package com.movierecommendations.api.recommendation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.movierecommendations.api.movie.Movie;
import com.movierecommendations.api.movie.MovieRepository;
import com.movierecommendations.api.user.User;
import com.movierecommendations.api.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
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

    public Recommendation addNewRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = new Recommendation();

        Optional<User> optionalUser = userRepository.findById(recommendationDTO.getUserId());
        User userFromDatabase = optionalUser.orElseThrow(() -> new IllegalStateException("No user has the ID: " + recommendationDTO.getUserId()));

        Optional<Movie> optionalMovie = movieRepository.findById(recommendationDTO.getMovieId());
        Movie movieFromDatabase = optionalMovie.orElseThrow(() -> new IllegalStateException("No movie has the ID: " + recommendationDTO.getMovieId()));

        recommendation.setUser(userFromDatabase);
        recommendation.setMovie(movieFromDatabase);

        if(recommendationDTO.getScore() == null) {
            throw new IllegalStateException("Recommendation not created, score can not be null.");
        }
        else if(recommendationDTO.getScore() < 0) {
            throw new IllegalStateException("Recommendation not created, score can't be lower than 0.");
        }
        else if(recommendationDTO.getScore() > 10) {
            throw new IllegalStateException("Recommendation not created, score can't be higher than 10.");
        }

        recommendation.setScore(recommendationDTO.getScore());
        recommendation.setRecommendationDate(LocalDate.now());

        return recommendationRepository.save(recommendation);
    }

    @Transactional
    public Recommendation updateRecommendation(Long recommendationId, RecommendationDTO updatedRecommendationDTO) {
        Recommendation recommendationFromDatabase = recommendationRepository.findById(recommendationId).orElseThrow(() -> new IllegalStateException("No recommendation has the ID: " + recommendationId));

        Optional<User> optionalUser = userRepository.findById(updatedRecommendationDTO.getUserId());
        User userFromDatabase = optionalUser.orElseThrow(() -> new IllegalStateException("No user has the ID: " + updatedRecommendationDTO.getUserId()));

        Optional<Movie> optionalMovie = movieRepository.findById(updatedRecommendationDTO.getMovieId());
        Movie movieFromDatabase = optionalMovie.orElseThrow(() -> new IllegalStateException("No movie has the ID: " + updatedRecommendationDTO.getMovieId()));

        recommendationFromDatabase.setUser(userFromDatabase);
        recommendationFromDatabase.setMovie(movieFromDatabase);

        if(updatedRecommendationDTO.getScore() == null) {
            throw new IllegalStateException("Recommendation not created, score can not be null.");
        }
        else if(updatedRecommendationDTO.getScore() < 0) {
            throw new IllegalStateException("Recommendation not created, score can't be lower than 0.");
        }
        else if(updatedRecommendationDTO.getScore() > 10) {
            throw new IllegalStateException("Recommendation not created, score can't be higher than 10.");
        }

        recommendationFromDatabase.setScore(updatedRecommendationDTO.getScore());

        return recommendationRepository.save(recommendationFromDatabase);
    }

    public void deleteRecommendation(Long recommendationId) {
        if(recommendationRepository.existsById(recommendationId) == false) {
            throw new IllegalStateException("No recommendation has the ID: " + recommendationId);
        }

        recommendationRepository.deleteById(recommendationId);
    }
}
