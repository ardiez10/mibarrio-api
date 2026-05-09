package com.mibarrio.api.exception;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Respuesta estándar de error de la API")
@Data
@AllArgsConstructor
public class ApiErrorResponse {

    @Schema(description = "Tipo de error", example = "Validation error")
    private String error;

    @Schema(description = "Detalle de los errores", example = "[\"El nombre es obligatorio\"]")
    private List<String> details;
}