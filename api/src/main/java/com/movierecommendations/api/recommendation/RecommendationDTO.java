package com.movierecommendations.api.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RecommendationDTO {
    private Long userId;

    private Long movieId;

    private Double score;
}
