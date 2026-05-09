package com.mibarrio.api.controller;

import com.mibarrio.api.dto.categoria.CategoriaResponse;
import com.mibarrio.api.dto.categoria.CreateCategoriaRequest;
import com.mibarrio.api.dto.categoria.UpdateCategoriaRequest;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.CategoriaService;
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

@Tag(name = "Categorías", description = "Operaciones para gestionar las categorías de comercios")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar categorías", description = "Devuelve todas las categorías activas.")
    @ApiResponse(responseCode = "200", description = "Listado de categorías obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getAll() {
        return ResponseEntity.ok(categoriaService.getAll());
    }

    @Operation(summary = "Obtener categoría por ID", description = "Devuelve el detalle de una categoría activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> getById(
            @Parameter(description = "Identificador de la categoría", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.getById(id));
    }

    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría de comercio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CategoriaResponse> create(
            @Valid @RequestBody CreateCategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.create(request));
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> update(
            @Parameter(description = "Identificador de la categoría", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.update(id, request));
    }

    @Operation(summary = "Eliminar categoría", description = "Realiza un borrado lógico de la categoría.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador de la categoría", example = "1")
            @PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}