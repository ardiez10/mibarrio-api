package com.mibarrio.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para registrarse como comercio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @Schema(description = "Nombre del usuario", example = "Cafeteria Central")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @Schema(description = "Email del usuario", example = "comercio@example.com")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Password123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,100}$", message = "La contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un número")
    private String password;

    @Schema(description = "Nombre del comercio", example = "Cafetería Central")
    @NotBlank(message = "El nombre del comercio es obligatorio")
    @Size(max = 100, message = "El nombre del comercio no puede superar los 100 caracteres")
    private String comercioNombre;

    @Schema(description = "Dirección del comercio", example = "Calle Mayor 10")
    @NotBlank(message = "La dirección del comercio es obligatoria")
    @Size(max = 150, message = "La dirección no puede superar los 150 caracteres")
    private String comercioDireccion;

    @Schema(description = "Teléfono de contacto", example = "666111222")
    @Pattern(regexp = "^\\+?[0-9\\s]{9,15}$", message = "El teléfono debe contener entre 9 y 15 dígitos y solo puede incluir un + al inicio")
    private String comercioTelefono;

    @Schema(description = "Identificador de la categoría", example = "1")
    @NotNull(message = "La categoría del comercio es obligatoria")
    private Long categoriaId;
}
