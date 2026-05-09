package com.mibarrio.api.dto.rol;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para crear un rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRolRequest {

    @Schema(description = "Nombre del rol", example = "COMERCIO")
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre del rol no puede superar los 50 caracteres")
    private String nombre;
}