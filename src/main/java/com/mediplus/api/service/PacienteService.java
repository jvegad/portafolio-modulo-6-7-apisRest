package com.mediplus.api.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mediplus.api.model.Paciente;

@Service
public class PacienteService {

    private final Map<Long, Paciente> pacientes = new HashMap<>();
    private long idCounter = 1;

    public Collection<Paciente> getAll() {
        return pacientes.values();
    }

    public Paciente getById(Long id) {
        return pacientes.get(id);
    }

    public Paciente create(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().isBlank()
                || paciente.getApellido() == null || paciente.getApellido().isBlank()
                || paciente.getEdad() == null || paciente.getEdad() < 0) {
            return null; // se manejará como 400 en el controlador
        }
        paciente.setId(idCounter++);
        pacientes.put(paciente.getId(), paciente);
        return paciente;
    }

    public Paciente update(Long id, Paciente paciente) {
        Paciente existente = pacientes.get(id);
        if (existente == null) {
            return null;
        }
        if (paciente.getNombre() != null) {
            existente.setNombre(paciente.getNombre());
        }
        if (paciente.getApellido() != null) {
            existente.setApellido(paciente.getApellido());
        }
        if (paciente.getEdad() != null) {
            existente.setEdad(paciente.getEdad());
        }
        return existente;
    }

    public boolean delete(Long id) {
        return pacientes.remove(id) != null;
    }
}
