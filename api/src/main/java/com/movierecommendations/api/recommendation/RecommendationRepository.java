package com.movierecommendations.api.recommendation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByUserUserId(Long userId);

    List<Recommendation> findByMovieMovieId(Long movieId);
}
