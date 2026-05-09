package com.mibarrio.api.dto.comercio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta con los datos de un comercio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComercioResponse {

    @Schema(description = "Identificador del comercio", example = "1")
    private Long id;

    @Schema(description = "Nombre del comercio", example = "Panadería Román")
    private String nombre;

    @Schema(description = "Dirección del comercio", example = "Calle Mayor 10")
    private String direccion;

    @Schema(description = "Teléfono de contacto", example = "666111222")
    private String telefono;

    @Schema(description = "Identificador de la categoría", example = "1")
    private Long categoriaId;

    @Schema(description = "Nombre de la categoría", example = "Alimentación")
    private String categoriaNombre;
}