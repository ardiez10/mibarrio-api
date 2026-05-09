package com.mibarrio.api.service;

import com.mibarrio.api.dto.usuario.CreateUsuarioRequest;
import com.mibarrio.api.dto.usuario.UpdateUsuarioRequest;
import com.mibarrio.api.dto.usuario.UsuarioResponse;
import com.mibarrio.api.model.Rol;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.RolRepository;
import com.mibarrio.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository) {

        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public List<UsuarioResponse> getAll() {
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UsuarioResponse getById(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    public UsuarioResponse create(CreateUsuarioRequest request) {

        usuarioRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Ya existe un usuario con ese email");
                });

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setActivo(true);
        usuario.setRol(rol);

        return mapToResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse update(Long id, UpdateUsuarioRequest request) {

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setRol(rol);

        return mapToResponse(usuarioRepository.save(usuario));
    }

    public void delete(Long id) {

        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);

        usuarioRepository.save(usuario);
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setEmail(usuario.getEmail());

        if (usuario.getRol() != null) {
            response.setRolId(usuario.getRol().getId());
            response.setRolNombre(usuario.getRol().getNombre());
        }

        response.setCreatedAt(usuario.getCreatedAt());
        response.setUpdatedAt(usuario.getUpdatedAt());

        return response;
    }
}