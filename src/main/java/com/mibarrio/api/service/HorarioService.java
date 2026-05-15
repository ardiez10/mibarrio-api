package com.mibarrio.api.service;

import com.mibarrio.api.dto.horario.CreateHorarioRequest;
import com.mibarrio.api.dto.horario.HorarioResponse;
import com.mibarrio.api.dto.horario.UpdateHorarioRequest;
import com.mibarrio.api.model.Comercio;
import com.mibarrio.api.model.Horario;
import com.mibarrio.api.repository.ComercioRepository;
import com.mibarrio.api.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final ComercioRepository comercioRepository;

    public HorarioService(HorarioRepository horarioRepository, ComercioRepository comercioRepository) {
        this.horarioRepository = horarioRepository;
        this.comercioRepository = comercioRepository;
    }

    public List<HorarioResponse> getAll() {
        return horarioRepository.findByActivoTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public HorarioResponse getById(Long id) {
        Horario horario = horarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        return mapToResponse(horario);
    }

    public List<HorarioResponse> getByComercioId(Long comercioId) {
        return horarioRepository.findByComercioIdAndActivoTrue(comercioId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public HorarioResponse create(Long comercioId, CreateHorarioRequest request) {
        Comercio comercio = comercioRepository.findByIdAndActivoTrue(comercioId)
                .orElseThrow(() -> new RuntimeException("Comercio no encontrado"));

        Horario horario = new Horario();
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraApertura(request.getHoraApertura());
        horario.setHoraCierre(request.getHoraCierre());
        horario.setActivo(true);
        horario.setComercio(comercio);

        if (request.getHoraCierre().isBefore(request.getHoraApertura())
                || request.getHoraCierre().equals(request.getHoraApertura())) {
            throw new RuntimeException(
                    "La hora de cierre debe ser posterior a la hora de apertura");
        }
        return mapToResponse(horarioRepository.save(horario));
    }

    public HorarioResponse update(Long id, UpdateHorarioRequest request) {
        Horario horario = horarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraApertura(request.getHoraApertura());
        horario.setHoraCierre(request.getHoraCierre());

        if (request.getHoraCierre().isBefore(request.getHoraApertura())
                || request.getHoraCierre().equals(request.getHoraApertura())) {
            throw new RuntimeException(
                    "La hora de cierre debe ser posterior a la hora de apertura");
        }
        return mapToResponse(horarioRepository.save(horario));
    }

    public void delete(Long id) {
        Horario horario = horarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        horario.setActivo(false);
        horarioRepository.save(horario);
    }

    private HorarioResponse mapToResponse(Horario horario) {
        HorarioResponse response = new HorarioResponse();

        response.setId(horario.getId());
        response.setDiaSemana(horario.getDiaSemana());
        response.setHoraApertura(horario.getHoraApertura());
        response.setHoraCierre(horario.getHoraCierre());

        if (horario.getComercio() != null) {
            response.setComercioId(horario.getComercio().getId());
            response.setComercioNombre(horario.getComercio().getNombre());
        }

        return response;
    }
}