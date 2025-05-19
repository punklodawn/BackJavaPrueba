package com.ejemplo.personas_api.controller;

import com.ejemplo.personas_api.DTOs.MovieDTO;
import com.ejemplo.personas_api.DTOs.PersonDTO;
import com.ejemplo.personas_api.mapper.*;
import com.ejemplo.personas_api.model.Movie;
import com.ejemplo.personas_api.model.Person;
import com.ejemplo.personas_api.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personas")
@Tag(name = "Gestión de Personas", description = "Endpoints para gestionar personas y sus películas favoritas")
public class PersonController {
    
    @Autowired
    private PersonService service;
    
    @Operation(
        summary = "Obtener todas las personas",
        description = "Devuelve una lista de todas las personas ordenadas por apellido y nombre",
        responses = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json"))
        }
    )
    @GetMapping
    public List<PersonDTO> getAll() {
        return service.getAll().stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }

    
    @Operation(
        summary = "Obtener persona por ID",
        description = "Devuelve los datos de una persona si existe",
        responses = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(PersonMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    

    @Operation(
        summary = "Buscar persona por nombre",
        description = "Devuelve un listado de personas que coincidan con el nombre dado"
    )
    @GetMapping("/search")
    public List<PersonDTO> getByName(@RequestParam String name) {
        return service.getByName(name).stream()
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Operation(
        summary = "Crear una nueva persona",
        description = "Guarda una nueva persona en el sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Persona creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidas")
        }
    )
    @PostMapping
    public ResponseEntity<PersonDTO> create(@RequestBody PersonDTO dto) {
         System.out.println("DTO recibido: " + dto);
        Person person = PersonMapper.toEntity(dto);
         System.out.println("Persona mapeada: " + person);
        Person created = service.create(person);
        PersonDTO responseDTO = PersonMapper.toDTO(created);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @Operation(
        summary = "Actualizar una persona",
        description = "Actualiza solo los campos enviados en la solicitud",
        responses = {
            @ApiResponse(responseCode = "200", description = "Persona actualizada"),
            @ApiResponse(responseCode = "400", description = "El ID no coincide con el del objeto"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<PersonDTO> update(@PathVariable Long id, @RequestBody @Valid PersonDTO dto) {
            if (!id.equals(dto.getId())) {
                return ResponseEntity.badRequest().build();
            }

            Person entity = PersonMapper.toEntity(dto);

            Optional<Person> optionalUpdated = service.update(id, entity);

            return optionalUpdated.map(updated -> {
                PersonDTO updatedDTO = PersonMapper.toDTO(updated);
                return ResponseEntity.ok(updatedDTO);
            }).orElse(ResponseEntity.notFound().build());
    }


    @Operation(
        summary = "Eliminar una persona",
        description = "Elimina permanentemente una persona del sistema",
        responses = {
            @ApiResponse(responseCode = "204", description = "Persona eliminada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
        summary = "Mostrar películas favoritas",
        description = "Devuelve la lista de películas favoritas de una persona"
    )
    @GetMapping("/{id}/movies")
     public List<MovieDTO> getMovies(@PathVariable Long id) {
        return service.getMovies(id).stream()
                .map(movie -> {
                    MovieDTO movieDTO = new MovieDTO();
                    movieDTO.setTitle(movie.getTitle());
                    movieDTO.setGenre(movie.getGenre());
                    return movieDTO;
                })
                .collect(Collectors.toList());
    }


    @Operation(
        summary = "Agregar una película a una persona",
        description = "Agrega una película a la lista de favoritos de una persona",
        responses = {
            @ApiResponse(responseCode = "200", description = "Película agregada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
        }
    )
    @PostMapping("/{id}/movies")
    public ResponseEntity<PersonDTO> addMovie(@PathVariable Long id, @RequestBody @Valid MovieDTO movieDTO) {
        Movie movie = MovieMapper.toEntity(movieDTO);
        return service.addMovie(id, movie)
                .map(PersonMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(
        summary = "Quitar una película de una persona",
        description = "Remueve una película de la lista de favoritos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Película quitada"),
            @ApiResponse(responseCode = "404", description = "Persona o película no encontrada")
        }
    )
    @DeleteMapping("/{id}/movies")
    public ResponseEntity<PersonDTO> removeMovie(@PathVariable Long id, @RequestParam String title) {
        return service.removeMovie(id, title)
                .map(PersonMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
