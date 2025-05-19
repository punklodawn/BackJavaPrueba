package com.ejemplo.personas_api.service;


import com.ejemplo.personas_api.model.Movie;
import com.ejemplo.personas_api.model.Person;
import com.ejemplo.personas_api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Value("${person.max-movies}")
    private int maxMovies;
 
    @Autowired
    private PersonRepository repository;

    public List<Person> getAll() {
    return repository.findAll().stream()
        .sorted((p1, p2) -> {
            int lastNameCmp = p1.getLastName().compareToIgnoreCase(p2.getLastName());
            return (lastNameCmp != 0) ? lastNameCmp : p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
        })
        .toList();
    }

    public Optional<Person> getById(Long id) {
        return repository.findById(id);
    }

    public List<Person> getByName(String name) {
        return repository.findByFirstName(name);
    }

    public Person create(Person p) {
        return repository.save(p);
    }

    public Optional<Person> update(Long id, Person updateData) {
        return repository.findById(id).map(existing -> {
            if (updateData.getFirstName() != null) existing.setFirstName(updateData.getFirstName());
            if (updateData.getLastName() != null) existing.setLastName(updateData.getLastName());
            if (updateData.getBirthdate() != null) existing.setBirthdate(updateData.getBirthdate());
            existing.setHasInsurance(updateData.isHasInsurance());
            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Movie> getMovies(Long personId) {
        return repository.findById(personId)
                .map(Person::getFavouriteMovies)
                .orElse(List.of());
    }

    public Optional<Person> addMovie(Long id, Movie movie) {
        return repository.findById(id).map(p -> {
             if (p.getFavouriteMovies().size() >= maxMovies) {
                throw new IllegalStateException("La persona ya tiene el número máximo de películas permitidas.");
            }
            p.getFavouriteMovies().add(movie);
            return repository.save(p);
        });
    }

    public Optional<Person> removeMovie(Long id, String title) {
        return repository.findById(id).map(p -> {
            p.getFavouriteMovies().removeIf(m -> m.getTitle().equalsIgnoreCase(title));
            return repository.save(p);
        });
    }
}
