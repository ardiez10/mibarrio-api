package com.mibarrio.api.controller;

import com.mibarrio.api.dto.rol.CreateRolRequest;
import com.mibarrio.api.dto.rol.RolResponse;
import com.mibarrio.api.exception.ApiErrorResponse;
import com.mibarrio.api.service.RolService;
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

@Tag(name = "Roles", description = "Operaciones para gestionar roles de usuario")
@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @Operation(summary = "Listar roles", description = "Devuelve todos los roles disponibles.")
    @GetMapping
    public ResponseEntity<List<RolResponse>> getAll() {
        return ResponseEntity.ok(rolService.getAll());
    }

    @Operation(summary = "Obtener rol por ID", description = "Devuelve el detalle de un rol.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> getById(
            @Parameter(description = "Identificador del rol", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(rolService.getById(id));
    }

    @Operation(summary = "Crear rol", description = "Crea un nuevo rol de usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<RolResponse> create(@Valid @RequestBody CreateRolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.create(request));
    }
}