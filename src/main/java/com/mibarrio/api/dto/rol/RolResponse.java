package com.mibarrio.api.dto.rol;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta con los datos de un rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolResponse {

    @Schema(description = "Identificador del rol", example = "1")
    private Long id;

    @Schema(description = "Nombre del rol", example = "ADMIN")
    private String nombre;
}