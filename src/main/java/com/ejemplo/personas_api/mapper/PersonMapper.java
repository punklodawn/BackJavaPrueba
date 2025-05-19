package com.ejemplo.personas_api.mapper;


import com.ejemplo.personas_api.DTOs.*;
import com.ejemplo.personas_api.model.*;

import java.util.stream.Collectors;

public class PersonMapper {
    public static PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthdate(person.getBirthdate());
        dto.setHasInsurance(person.isHasInsurance());
        dto.setFavouriteMovies(person.getFavouriteMovies().stream().map(movie -> {
            MovieDTO m = new MovieDTO();
            m.setTitle(movie.getTitle());
            m.setGenre(movie.getGenre());
            return m;
        }).collect(Collectors.toList()));
        return dto;
    }

    public static Person toEntity(PersonDTO dto) {
        Person p = new Person();
        p.setId(dto.getId());
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setBirthdate(dto.getBirthdate());
        p.setHasInsurance(dto.isHasInsurance());
        p.setFavouriteMovies(dto.getFavouriteMovies().stream().map(movieDTO -> {
            Movie m = new Movie();
            m.setTitle(movieDTO.getTitle());
            m.setGenre(movieDTO.getGenre());
            return m;
        }).collect(Collectors.toList()));
        return p;
    }
}
