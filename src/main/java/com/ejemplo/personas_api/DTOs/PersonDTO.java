package com.ejemplo.personas_api.DTOs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Datos de una persona")
public class PersonDTO {
    @NotNull(message = "El ID es requerido")
    @Schema(description = "Identificador único de la persona", example = "1")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre de la persona", example = "Pablo")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Schema(description = "Apellido de la persona", example = "Lamberti")
    private String lastName;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Schema(description = "Fecha de nacimiento", example = "1987-04-03")
    private LocalDate birthdate;

    @Schema(description = "Indica si tiene seguro médico", example = "false")
    private boolean hasInsurance;

    @Size(max = 5, message = "Una persona no puede tener más de 5 películas favoritas")
    @Schema(description = "Lista de películas favoritas")
    private List<MovieDTO> favouriteMovies = new ArrayList<>();
}
