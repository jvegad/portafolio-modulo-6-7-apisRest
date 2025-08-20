package com.mediplus.api.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediplus.api.model.Medico;
import com.mediplus.api.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    public ResponseEntity<Collection<Medico>> getAll() {
        return ResponseEntity.ok(medicoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getById(@PathVariable Long id) {
        Medico medico = medicoService.getById(id);
        return medico != null ? ResponseEntity.ok(medico) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Medico> create(@RequestBody Medico medico) {
        Medico creado = medicoService.create(medico);
        return creado != null ? ResponseEntity.status(201).body(creado) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> update(@PathVariable Long id, @RequestBody Medico medico) {
        Medico actualizado = medicoService.update(id, medico);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean eliminado = medicoService.delete(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
