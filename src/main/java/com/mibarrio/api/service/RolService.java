package com.mibarrio.api.service;

import com.mibarrio.api.dto.rol.CreateRolRequest;
import com.mibarrio.api.dto.rol.RolResponse;
import com.mibarrio.api.model.Rol;
import com.mibarrio.api.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<RolResponse> getAll() {
        return rolRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RolResponse getById(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        return mapToResponse(rol);
    }

    public RolResponse create(CreateRolRequest request) {

        rolRepository.findByNombre(request.getNombre())
                .ifPresent(r -> {
                    throw new RuntimeException("Ya existe un rol con ese nombre");
                });

        Rol rol = new Rol();
        rol.setNombre(request.getNombre());

        return mapToResponse(rolRepository.save(rol));
    }

    private RolResponse mapToResponse(Rol rol) {
        RolResponse response = new RolResponse();

        response.setId(rol.getId());
        response.setNombre(rol.getNombre());

        return response;
    }
}