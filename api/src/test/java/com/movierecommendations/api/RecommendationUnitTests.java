package com.movierecommendations.api;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.movierecommendations.api.movie.Movie;
import com.movierecommendations.api.recommendation.Recommendation;
import com.movierecommendations.api.recommendation.RecommendationRepository;
import com.movierecommendations.api.recommendation.RecommendationService;
import com.movierecommendations.api.user.User;

@ExtendWith(MockitoExtension.class)
public class RecommendationUnitTests {
    @InjectMocks
    private RecommendationService recommendationService;

    @Mock
    private RecommendationRepository recommendationRepository;

    @Test
    void reccomendationHasRightAttributes() {
        Long recommendationId = 1L;
        Double score = 10.0;
        LocalDate recommendationDate = LocalDate.now();

        User userTestOne = new User(1L, "John", "john@mail.com");
        User userTestTwo = new User(1L, "John", "john@mail.com");

        Movie movieTestOne = new Movie(1L, "The RUT Movie", "Action", "John", "USA", 2023);
        Movie movieTestTwo = new Movie(1L, "The RUT Movie", "Action", "John", "USA", 2023);

        Recommendation recommendationConstructor = new Recommendation(recommendationId, userTestOne, movieTestOne, score, recommendationDate);
        Recommendation recommendationSetter = new Recommendation();

        recommendationSetter.setRecommendationId(recommendationId);
        recommendationSetter.setUser(userTestTwo);
        recommendationSetter.setMovie(movieTestTwo);
        recommendationSetter.setScore(score);
        recommendationSetter.setRecommendationDate(recommendationDate);

        Mockito.when(recommendationRepository.save(any(Recommendation.class))).then(AdditionalAnswers.returnsFirstArg());

        Recommendation testRecommendation = recommendationRepository.save(recommendationSetter);

        Assertions.assertEquals(testRecommendation.getRecommendationId(), recommendationConstructor.getRecommendationId());
        Assertions.assertEquals(testRecommendation.getUser(), recommendationConstructor.getUser());
        Assertions.assertEquals(testRecommendation.getMovie(), recommendationConstructor.getMovie());
        Assertions.assertEquals(testRecommendation.getScore(), recommendationConstructor.getScore());
        Assertions.assertEquals(testRecommendation.getRecommendationDate(), recommendationConstructor.getRecommendationDate());
    }
}
