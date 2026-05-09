package com.mibarrio.api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Respuesta con los datos de un usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    @Schema(description = "Identificador del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Alba Díez")
    private String nombre;

    @Schema(description = "Email del usuario", example = "alba@example.com")
    private String email;

    @Schema(description = "Identificador del rol", example = "2")
    private Long rolId;

    @Schema(description = "Nombre del rol", example = "COMERCIO")
    private String rolNombre;

    @Schema(description = "Fecha de creación del usuario", example = "2026-05-09T11:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización del usuario", example = "2026-05-09T12:45:00")
    private LocalDateTime updatedAt;
}