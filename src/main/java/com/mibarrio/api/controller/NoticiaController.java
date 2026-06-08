package com.mibarrio.api.controller;

import com.mibarrio.api.dto.noticia.CreateNoticiaRequest;
import com.mibarrio.api.dto.noticia.NoticiaResponse;
import com.mibarrio.api.dto.noticia.UpdateNoticiaRequest;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.NoticiaService;
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

@Tag(name = "Noticias", description = "Operaciones para gestionar noticias del barrio")
@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @Operation(summary = "Listar noticias", description = "Devuelve todas las noticias activas.")
    @ApiResponse(responseCode = "200", description = "Listado de noticias obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<NoticiaResponse>> getAll() {
        return ResponseEntity.ok(noticiaService.getAll());
    }

    @Operation(summary = "Listar todas las noticias para administración", description = "Devuelve todas las noticias, activas e inactivas.")
    @GetMapping("/admin")
    public ResponseEntity<List<NoticiaResponse>> getAllForAdmin() {
        return ResponseEntity.ok(noticiaService.getAllForAdmin());
    }

    @Operation(summary = "Obtener noticia por ID", description = "Devuelve el detalle de una noticia activa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Noticia encontrada"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoticiaResponse> getById(
            @Parameter(description = "Identificador de la noticia", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(noticiaService.getById(id));
    }

    @Operation(summary = "Crear noticia", description = "Crea una nueva noticia del barrio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Noticia creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<NoticiaResponse> create(
            @Valid @RequestBody CreateNoticiaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noticiaService.create(request));
    }

    @Operation(summary = "Actualizar noticia", description = "Actualiza los datos de una noticia existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Noticia actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoticiaResponse> update(
            @Parameter(description = "Identificador de la noticia", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateNoticiaRequest request) {
        return ResponseEntity.ok(noticiaService.update(id, request));
    }

    @Operation(summary = "Eliminar noticia", description = "Realiza un borrado lógico de la noticia.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Noticia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Noticia no encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador de la noticia", example = "1")
            @PathVariable Long id) {
        noticiaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar noticia", description = "Vuelve a mostrar una noticia desactivada.")
    @PutMapping("/{id}/activar")
    public ResponseEntity<NoticiaResponse> activate(@PathVariable Long id) {
        return ResponseEntity.ok(noticiaService.activate(id));
    }
}
