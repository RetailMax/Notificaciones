package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import com.retailmax.notifications.service.EstadoDeNotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-notificacion")
public class EstadoDeNotificacionController {

    private final EstadoDeNotificacionService service;

    public EstadoDeNotificacionController(EstadoDeNotificacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstadoDeNotificacion> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDeNotificacion> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EstadoDeNotificacion create(@RequestBody EstadoDeNotificacion estado) {
        return service.save(estado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}