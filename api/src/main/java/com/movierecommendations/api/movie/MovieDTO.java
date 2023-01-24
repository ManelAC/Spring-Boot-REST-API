package com.movierecommendations.api.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MovieDTO {
    private String title;

    private String genre;

    private String director;

    private String country;

    private Integer releaseYear;
}
