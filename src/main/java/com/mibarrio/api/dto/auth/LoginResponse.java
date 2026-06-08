package com.mibarrio.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta del login")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    @Schema(description = "Token JWT del usuario")
    private String token;

    @Schema(description = "Tipo de token", example = "Bearer")
    private String type;

    @Schema(description = "Email del usuario autenticado")
    private String email;

    @Schema(description = "Identificador del usuario autenticado")
    private Long userId;

    @Schema(description = "Rol del usuario")
    private String rol;
}
