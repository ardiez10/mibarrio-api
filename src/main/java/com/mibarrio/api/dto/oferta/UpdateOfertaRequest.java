package com.mibarrio.api.dto.oferta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para actualizar una oferta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOfertaRequest {

    @Schema(
            description = "Título de la oferta",
            example = "2x1 en café"
    )
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    private String titulo;

    @Schema(
            description = "Descripción de la oferta",
            example = "Oferta disponible durante esta semana"
    )
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 300, message = "La descripción no puede superar los 300 caracteres")
    private String descripcion;
}