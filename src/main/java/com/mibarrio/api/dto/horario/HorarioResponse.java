package com.mibarrio.api.dto.horario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Schema(description = "Respuesta con los datos de un horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioResponse {

    @Schema(description = "Identificador del horario", example = "1")
    private Long id;

    @Schema(description = "Día de la semana. 1 = lunes, 7 = domingo", example = "1")
    private Integer diaSemana;

    @Schema(description = "Hora de apertura", example = "09:00")
    private LocalTime horaApertura;

    @Schema(description = "Hora de cierre", example = "14:00")
    private LocalTime horaCierre;

    @Schema(description = "Identificador del comercio asociado", example = "1")
    private Long comercioId;

    @Schema(description = "Nombre del comercio asociado", example = "Panadería Román")
    private String comercioNombre;
}