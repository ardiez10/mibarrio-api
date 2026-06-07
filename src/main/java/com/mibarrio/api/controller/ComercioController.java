package com.mibarrio.api.controller;

import com.mibarrio.api.dto.comercio.ComercioResponse;
import com.mibarrio.api.dto.comercio.CreateComercioRequest;
import com.mibarrio.api.dto.comercio.UpdateComercioRequest;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.ComercioService;
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

@Tag(name = "Comercios", description = "Operaciones para gestionar comercios locales")
@RestController
@RequestMapping("/api/comercios")
public class ComercioController {

    private final ComercioService comercioService;

    public ComercioController(ComercioService comercioService) {
        this.comercioService = comercioService;
    }

    @Operation(summary = "Listar comercios", description = "Devuelve todos los comercios activos.")
    @ApiResponse(responseCode = "200", description = "Listado de comercios obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ComercioResponse>> getAll() {
        return ResponseEntity.ok(comercioService.getAll());
    }

    @Operation(summary = "Listar comercios pendientes", description = "Devuelve las solicitudes de comercios pendientes de aprobación.")
    @GetMapping("/pendientes")
    public ResponseEntity<List<ComercioResponse>> getPendientes() {
        return ResponseEntity.ok(comercioService.getPendientes());
    }

    @Operation(summary = "Obtener comercio por ID", description = "Devuelve el detalle de un comercio activo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comercio encontrado"),
            @ApiResponse(responseCode = "404", description = "Comercio no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ComercioResponse> getById(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(comercioService.getById(id));
    }

    @Operation(summary = "Crear comercio", description = "Crea un nuevo comercio asociado a una categoría existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comercio creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ComercioResponse> create(
            @Valid @RequestBody CreateComercioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comercioService.create(request));
    }

    @Operation(summary = "Actualizar comercio", description = "Actualiza los datos de un comercio existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comercio actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Comercio o categoría no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ComercioResponse> update(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateComercioRequest request) {
        return ResponseEntity.ok(comercioService.update(id, request));
    }

    @Operation(summary = "Eliminar comercio", description = "Realiza un borrado lógico del comercio.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comercio eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Comercio no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long id) {
        comercioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Aprobar comercio", description = "Aprueba una solicitud de comercio pendiente.")
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<ComercioResponse> approve(@PathVariable Long id) {
        return ResponseEntity.ok(comercioService.approve(id));
    }

    @Operation(summary = "Rechazar comercio", description = "Rechaza una solicitud de comercio pendiente.")
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<ComercioResponse> reject(@PathVariable Long id) {
        return ResponseEntity.ok(comercioService.reject(id));
    }
}
