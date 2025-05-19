package com.ejemplo.personas_api.mapper;

import com.ejemplo.personas_api.DTOs.MovieDTO;
import com.ejemplo.personas_api.model.Movie;

public class MovieMapper {

    public static MovieDTO toDTO(Movie movie) {
        if (movie == null) return null;

        MovieDTO dto = new MovieDTO();
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());

        return dto;
    }
    
    public static Movie toEntity(MovieDTO dto) {
        if (dto == null) return null;

        Movie entity = new Movie();
        entity.setTitle(dto.getTitle());
        entity.setGenre(dto.getGenre());

        return entity;
    }
}
