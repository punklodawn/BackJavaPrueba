package com.ejemplo.personas_api.DTOs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos de una película")
public class MovieDTO {
    @NotBlank(message = "El título es requerido")
    @Schema(description = "Título de la película", example = "The Lord of the Rings")
    private String title;

    @NotBlank(message = "El género es requerido")
    @Schema(description = "Género de la película", example = "fantasy")
    private String genre;
}
