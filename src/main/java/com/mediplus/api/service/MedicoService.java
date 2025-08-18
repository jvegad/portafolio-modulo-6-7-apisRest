package com.mediplus.api.service;

import com.mediplus.api.model.Medico;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MedicoService {

    private final Map<Long, Medico> medicos = new HashMap<>();
    private long idCounter = 1;

    public Collection<Medico> getAll() {
        return medicos.values();
    }

    public Medico getById(Long id) {
        return medicos.get(id);
    }

    public Medico create(Medico medico) {
        if (medico.getNombre() == null || medico.getApellido() == null) {
            return null; // se manejará como 400 en el controlador
        }
        medico.setId(idCounter++);
        medicos.put(medico.getId(), medico);
        return medico;
    }

    public Medico update(Long id, Medico medico) {
        Medico existente = medicos.get(id);
        if (existente == null) return null;
        if (medico.getNombre() != null) existente.setNombre(medico.getNombre());
        if (medico.getApellido() != null) existente.setApellido(medico.getApellido());
        if (medico.getEspecialidad() != null) existente.setEspecialidad(medico.getEspecialidad());
        return existente;
    }

    public boolean delete(Long id) {
        return medicos.remove(id) != null;
    }
}
