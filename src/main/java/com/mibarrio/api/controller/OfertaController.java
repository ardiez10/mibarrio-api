package com.mibarrio.api.controller;

import com.mibarrio.api.dto.oferta.CreateOfertaRequest;
import com.mibarrio.api.dto.oferta.OfertaResponse;
import com.mibarrio.api.dto.oferta.UpdateOfertaRequest;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.OfertaService;
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

@Tag(name = "Ofertas", description = "Operaciones para gestionar ofertas de comercios")
@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @Operation(summary = "Listar ofertas", description = "Devuelve todas las ofertas activas.")
    @ApiResponse(responseCode = "200", description = "Listado de ofertas obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<OfertaResponse>> getAll() {
        return ResponseEntity.ok(ofertaService.getAll());
    }

    @Operation(summary = "Obtener oferta por ID", description = "Devuelve el detalle de una oferta activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta encontrada"),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OfertaResponse> getById(
            @Parameter(description = "Identificador de la oferta", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(ofertaService.getById(id));
    }

    @Operation(summary = "Listar ofertas de un comercio", description = "Devuelve todas las ofertas activas asociadas a un comercio.")
    @ApiResponse(responseCode = "200", description = "Listado de ofertas obtenido correctamente")
    @GetMapping("/comercio/{comercioId}")
    public ResponseEntity<List<OfertaResponse>> getByComercioId(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long comercioId) {
        return ResponseEntity.ok(ofertaService.getByComercioId(comercioId));
    }

    @Operation(summary = "Crear oferta", description = "Crea una nueva oferta asociada a un comercio existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Oferta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Comercio no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/comercio/{comercioId}")
    public ResponseEntity<OfertaResponse> create(
            @Parameter(description = "Identificador del comercio", example = "1")
            @PathVariable Long comercioId,
            @Valid @RequestBody CreateOfertaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ofertaService.create(comercioId, request));
    }

    @Operation(summary = "Actualizar oferta", description = "Actualiza los datos de una oferta existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Oferta actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<OfertaResponse> update(
            @Parameter(description = "Identificador de la oferta", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateOfertaRequest request) {
        return ResponseEntity.ok(ofertaService.update(id, request));
    }

    @Operation(summary = "Eliminar oferta", description = "Realiza un borrado lógico de la oferta.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Oferta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Oferta no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador de la oferta", example = "1")
            @PathVariable Long id) {
        ofertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}