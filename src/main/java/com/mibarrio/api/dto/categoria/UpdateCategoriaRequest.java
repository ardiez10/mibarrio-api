package com.mibarrio.api.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para actualizar una categoría")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoriaRequest {

    @Schema(
            description = "Nombre de la categoría",
            example = "Alimentación"
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;
}