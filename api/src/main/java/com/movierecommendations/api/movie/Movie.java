package com.movierecommendations.api.movie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table(name="movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long movieId;
    
    @NotNull
    @NotBlank(message = "Movie's title can not be blank.")
    @NotEmpty(message = "Movie's title can not be empty.")
    private String title;

    @NotNull
    @NotBlank(message = "Movie's genre can not be blank.")
    @NotEmpty(message = "Movie's genre can not be empty.")
    private String genre;

    @NotNull
    @NotBlank(message = "Movie's director can not be blank.")
    @NotEmpty(message = "Movie's director can not be empty.")
    private String director;

    @NotNull
    @NotBlank(message = "Movie's country can not be blank.")
    @NotEmpty(message = "Movie's country can not be empty.")
    private String country;

    @NotNull
    private Integer releaseYear;
}
