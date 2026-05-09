package com.mibarrio.api.dto.oferta;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta con los datos de una oferta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaResponse {

    @Schema(
            description = "Identificador de la oferta",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Título de la oferta",
            example = "2x1 en café"
    )
    private String titulo;

    @Schema(
            description = "Descripción de la oferta",
            example = "Oferta disponible durante esta semana"
    )
    private String descripcion;

    @Schema(
            description = "Identificador del comercio asociado",
            example = "1"
    )
    private Long comercioId;

    @Schema(
            description = "Nombre del comercio asociado",
            example = "Cafetería Central"
    )
    private String comercioNombre;
}