package com.mibarrio.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mibarrio.api.dto.comercio.ComercioResponse;
import com.mibarrio.api.dto.comercio.CreateComercioRequest;
import com.mibarrio.api.dto.comercio.UpdateComercioRequest;
import com.mibarrio.api.model.Categoria;
import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.CategoriaRepository;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.UsuarioRepository;

@Service
public class ComercioService {

    private final ComercioRepository comercioRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    private final AuthorizationService authorizationService;

    public ComercioService(
            ComercioRepository comercioRepository,
            CategoriaRepository categoriaRepository,
            UsuarioRepository usuarioRepository,
            AuthorizationService authorizationService) {

        this.comercioRepository = comercioRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.authorizationService = authorizationService;
    }

    public List<ComercioResponse> getAll() {
        return comercioRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ComercioResponse getById(Long id) {
        Comercio comercio = comercioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        return mapToResponse(comercio);
    }

    public ComercioResponse create(CreateComercioRequest request) {
        Comercio comercio = new Comercio();

        comercio.setNombre(request.getNombre());
        comercio.setDireccion(request.getDireccion());
        comercio.setTelefono(request.getTelefono());
        comercio.setActivo(true);

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        comercio.setCategoria(categoria);

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        comercio.setUsuario(usuario);

        Comercio savedComercio = comercioRepository.save(comercio);

        return mapToResponse(savedComercio);
    }

    public ComercioResponse update(Long id, UpdateComercioRequest request) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        if (!authorizationService.canManageComercio(comercio)) {
            throw new RuntimeException("No tienes permisos para modificar este comercio");
        }

        comercio.setNombre(request.getNombre());
        comercio.setDireccion(request.getDireccion());
        comercio.setTelefono(request.getTelefono());

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        comercio.setCategoria(categoria);

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        comercio.setUsuario(usuario);

        Comercio updatedComercio = comercioRepository.save(comercio);

        return mapToResponse(updatedComercio);
    }

    public void delete(Long id) {
        Comercio comercio = comercioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        if (!authorizationService.canManageComercio(comercio)) {
            throw new RuntimeException("No tienes permisos para eliminar este comercio");
        }

        comercio.setActivo(false);
        comercioRepository.save(comercio);
    }

    private ComercioResponse mapToResponse(Comercio comercio) {
        ComercioResponse response = new ComercioResponse();

        response.setId(comercio.getId());
        response.setNombre(comercio.getNombre());
        response.setDireccion(comercio.getDireccion());
        response.setTelefono(comercio.getTelefono());

        if (comercio.getCategoria() != null) {
            response.setCategoriaId(comercio.getCategoria().getId());
            response.setCategoriaNombre(comercio.getCategoria().getNombre());
        }

        response.setCreatedAt(comercio.getCreatedAt());
        response.setUpdatedAt(comercio.getUpdatedAt());

        if (comercio.getUsuario() != null) {
            response.setUsuarioId(comercio.getUsuario().getId());
            response.setUsuarioNombre(comercio.getUsuario().getNombre());
        }

        return response;
    }
}