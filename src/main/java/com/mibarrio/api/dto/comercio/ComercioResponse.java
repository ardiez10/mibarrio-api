package com.mibarrio.api.dto.comercio;

import java.time.LocalDateTime;
import java.util.List;

import com.mibarrio.api.dto.horario.HorarioResponse;
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
    private String categoria;

    @Schema(description = "Fecha de creación del comercio", example = "2026-05-09T11:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización del comercio", example = "2026-05-09T12:45:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Identificador del usuario gestor del comercio", example = "1")
    private Long usuarioId;

    @Schema(description = "Nombre del usuario gestor del comercio", example = "Panadería Román")
    private String usuarioNombre;

    @Schema(description = "Email del usuario gestor del comercio", example = "panaderia@example.com")
    private String usuarioEmail;

    private Boolean activo;

    private String estado;

    private String imagenUrl;

    @Schema(description = "Horarios activos del comercio")
    private List<HorarioResponse> horarios;
}
