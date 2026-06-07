package com.mibarrio.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Respuesta del registro público")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {

    @Schema(description = "Email registrado")
    private String email;

    @Schema(description = "Estado asignado a la cuenta", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Mensaje informativo para el usuario")
    private String message;
}
