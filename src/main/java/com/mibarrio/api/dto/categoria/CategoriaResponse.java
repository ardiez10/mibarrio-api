package com.mibarrio.api.dto.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta con los datos de una categoría")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponse {

    @Schema(
            description = "Identificador de la categoría",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre de la categoría",
            example = "Alimentación"
    )
    private String nombre;
}