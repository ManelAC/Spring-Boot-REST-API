package com.movierecommendations.api.movie;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieConverter {
    public MovieDTO convertEntityToDTO(Movie movie) {
        ModelMapper modelMapper = new ModelMapper();
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
        return movieDTO;
    }
    
    public Movie convertDTOToEntity(MovieDTO movieDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        return movie;
    }
}
