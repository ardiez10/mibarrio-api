package com.mibarrio.api.service;

import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Usuario;
import com.mibarrio.api.exception.ForbiddenException;
import com.mibarrio.api.exception.UnauthorizedException;
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
                .orElseThrow(() -> new UnauthorizedException("Usuario autenticado no encontrado"));
    }

    public boolean canManageComercio(Comercio comercio) {

        Usuario currentUser = getCurrentUser();

        if (isAdmin(currentUser)) {
            return true;
        }

        return comercio.getUsuario().getId().equals(currentUser.getId());
    }

    public boolean isAdmin() {
        return isAdmin(getCurrentUser());
    }

    public Usuario getCurrentUserOrThrowIfNotAdminManaging(Long usuarioId) {
        Usuario currentUser = getCurrentUser();

        if (!isAdmin(currentUser) && !currentUser.getId().equals(usuarioId)) {
            throw new ForbiddenException("No tienes permisos para operar sobre otro usuario");
        }

        return currentUser;
    }

    private boolean isAdmin(Usuario user) {
        return "ADMIN".equals(user.getRol().getNombre());
    }
}
