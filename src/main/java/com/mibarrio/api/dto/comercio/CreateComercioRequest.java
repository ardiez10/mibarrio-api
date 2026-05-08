package com.mibarrio.api.dto.comercio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateComercioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 150, message = "La dirección no puede superar los 150 caracteres")
    private String direccion;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    private String telefono;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

}