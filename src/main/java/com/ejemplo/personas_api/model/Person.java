package com.ejemplo.personas_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private boolean hasInsurance;
    private List<Movie> favouriteMovies = new ArrayList<>();
    
}
