package com.mibarrio.api.service;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.repository.UsuarioRepository;
import com.mibarrio.api.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UsuarioRepository usuarioRepository;

    public AuthorizationService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getCurrentUser() {

        String email = SecurityUtils.getCurrentUserEmail();

        return usuarioRepository.findByEmailAndActivoTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    public boolean canManageComercio(Comercio comercio) {

        Usuario currentUser = getCurrentUser();

        String role = currentUser.getRol().getNombre();

        if ("ADMIN".equals(role)) {
            return true;
        }

        return comercio.getUsuario().getId().equals(currentUser.getId());
    }
}