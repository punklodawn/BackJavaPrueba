package com.ejemplo.personas_api.repository;


import com.ejemplo.personas_api.model.Person;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepository {
    
    private final Map<Long, Person> people = new HashMap<>();
    private Long nextId = 1L;

    public List<Person> findAll() {
        return people.values().stream()
                .sorted(Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName))
                .toList();
    }

    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(people.get(id));
    }

    public List<Person> findByFirstName(String name) {
        return people.values().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(name))
                .toList();
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(nextId++);
        }
        people.put(person.getId(), person);
        return person;
    }

    public void delete(Long id) {
        people.remove(id);
    }
}
