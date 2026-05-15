package com.mibarrio.api.dto.comercio;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para actualizar un comercio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateComercioRequest {

    @Schema(description = "Nombre del comercio", example = "Panadería Román")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Schema(description = "Dirección del comercio", example = "Calle Mayor 10")
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 150, message = "La dirección no puede superar los 150 caracteres")
    private String direccion;

    @Schema(description = "Teléfono de contacto", example = "666111222")
    @Pattern(regexp = "^\\+?[0-9\\s]{9,15}$", message = "El teléfono debe contener entre 9 y 15 dígitos y solo puede incluir un + al inicio")
    private String telefono;

    @Schema(description = "Identificador de la categoría", example = "1")
    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    @Schema(description = "Identificador del usuario gestor del comercio", example = "1")
    @NotNull(message = "El usuario gestor es obligatorio")
    private Long usuarioId;
}