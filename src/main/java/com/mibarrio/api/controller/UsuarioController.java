package com.mibarrio.api.controller;

import com.mibarrio.api.dto.usuario.CreateUsuarioRequest;
import com.mibarrio.api.dto.usuario.UpdateUsuarioRequest;
import com.mibarrio.api.dto.usuario.UsuarioResponse;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.UsuarioService;
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

@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios del sistema")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Listar usuarios", description = "Devuelve todos los usuarios activos.")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @Operation(summary = "Listar usuarios pendientes", description = "Devuelve los comercios pendientes de aprobacion.")
    @GetMapping("/pendientes")
    public ResponseEntity<List<UsuarioResponse>> getPendientes() {
        return ResponseEntity.ok(usuarioService.getPendientes());
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve el detalle de un usuario activo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario asociado a un rol existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody CreateUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(request));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuario o rol no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    @Operation(summary = "Eliminar usuario", description = "Realiza un borrado lógico del usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador del usuario", example = "1")
            @PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Aprobar usuario", description = "Aprueba una cuenta pendiente.")
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<UsuarioResponse> approve(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.approve(id));
    }

    @Operation(summary = "Rechazar usuario", description = "Rechaza una cuenta pendiente.")
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<UsuarioResponse> reject(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.reject(id));
    }
}
