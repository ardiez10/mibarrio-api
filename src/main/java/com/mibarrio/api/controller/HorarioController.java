package com.mibarrio.api.controller;

import com.mibarrio.api.dto.horario.CreateHorarioRequest;
import com.mibarrio.api.dto.horario.HorarioResponse;
import com.mibarrio.api.dto.horario.UpdateHorarioRequest;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.HorarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Horarios", description = "Operaciones para gestionar horarios de comercios")
@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @Operation(summary = "Listar horarios", description = "Devuelve todos los horarios activos.")
    @ApiResponse(responseCode = "200", description = "Listado de horarios obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<HorarioResponse>> getAll() {
        return ResponseEntity.ok(horarioService.getAll());
    }

    @Operation(summary = "Obtener horario por ID", description = "Devuelve el detalle de un horario activo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horario encontrado"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponse> getById(
            @Parameter(description = "Identificador del horario", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(horarioService.getById(id));
    }

    @Operation(summary = "Listar horarios de un comercio", description = "Devuelve todos los horarios activos asociados a un comercio.")
    @ApiResponse(responseCode = "200", description = "Listado de horarios obtenido correctamente")
    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<HorarioResponse>> getByComercioId(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long comercioId) {
        return ResponseEntity.ok(horarioService.getByComercioId(comercioId));
    }

    @Operation(summary = "Crear horario", description = "Crea un nuevo horario asociado a un comercio existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Horario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Comercio no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/comercio/{comercioId}")
    public ResponseEntity<HorarioResponse> create(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long comercioId,
            @Valid @RequestBody CreateHorarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.create(comercioId, request));
    }

    @Operation(summary = "Actualizar horario", description = "Actualiza los datos de un horario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Horario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponse> update(
            @Parameter(description = "Identificador del horario", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateHorarioRequest request) {
        return ResponseEntity.ok(horarioService.update(id, request));
    }

    @Operation(summary = "Eliminar horario", description = "Realiza un borrado lógico del horario.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Horario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Horario no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador del horario", example = "1")
            @PathVariable Long id) {
        horarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}