package com.mibarrio.api.dto.horario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Schema(description = "Datos necesarios para actualizar un horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHorarioRequest {

    @Schema(description = "Día de la semana. 1 = lunes, 7 = domingo", example = "1")
    @NotNull(message = "El día de la semana es obligatorio")
    @Min(value = 1, message = "El día debe estar entre 1 y 7")
    @Max(value = 7, message = "El día debe estar entre 1 y 7")
    private Integer diaSemana;

    @Schema(description = "Hora de apertura", example = "09:00")
    @NotNull(message = "La hora de apertura es obligatoria")
    private LocalTime horaApertura;

    @Schema(description = "Hora de cierre", example = "14:00")
    @NotNull(message = "La hora de cierre es obligatoria")
    private LocalTime horaCierre;
}