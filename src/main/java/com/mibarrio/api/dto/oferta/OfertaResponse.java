package com.mibarrio.api.dto.oferta;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta con los datos de una oferta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaResponse {

    @Schema(description = "Identificador de la oferta", example = "1")
    private Long id;

    @Schema(description = "Título de la oferta", example = "2x1 en café")
    private String titulo;

    @Schema(description = "Descripción de la oferta", example = "Oferta disponible durante esta semana")
    private String descripcion;

    @Schema(description = "Indica si la oferta está activa", example = "true")
    private Boolean activo;

    @Schema(description = "Identificador del comercio asociado", example = "1")
    private Long comercioId;

    @Schema(description = "Nombre del comercio asociado", example = "Cafetería Central")
    private String comercioNombre;

    @Schema(description = "Imagen del comercio asociado", example = "https://example.com/comercio.jpg")
    private String comercioImagenUrl;

    @Schema(description = "Categoría del comercio asociado", example = "Hostelería")
    private String categoria;

    @Schema(description = "Fecha hasta la que la oferta estará disponible", example = "2026-06-30")
    private LocalDate fechaFin;

    @Schema(description = "Fecha de creación de la oferta", example = "2026-05-09T11:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización de la oferta", example = "2026-05-09T12:45:00")
    private LocalDateTime updatedAt;
}
