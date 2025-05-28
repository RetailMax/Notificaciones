package com.notificaciones.notificacionesFullstack.controller;

import com.notificaciones.notificacionesFullstack.model.PlantillaNotificacion;
import com.notificaciones.notificacionesFullstack.service.PlantillaNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plantillas")
public class PlantillaNotificacionController {

    @Autowired
    private PlantillaNotificacionService service;

    // Crear una nueva plantilla
    @PostMapping
    public ResponseEntity<PlantillaNotificacion> crear(@RequestBody PlantillaNotificacion plantilla) {
        return ResponseEntity.ok(service.guardar(plantilla));
    }

    // Listar todas las plantillas
    @GetMapping
    public ResponseEntity<List<PlantillaNotificacion>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Obtener una plantilla por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlantillaNotificacion> obtener(@PathVariable Integer id) {
        PlantillaNotificacion plantilla = service.buscarPorId(id);
        if (plantilla != null) {
            return ResponseEntity.ok(plantilla);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener solo el contenido de una plantilla
    @GetMapping("/{id}/contenido")
    public ResponseEntity<String> obtenerContenido(@PathVariable Integer id) {
        PlantillaNotificacion plantilla = service.buscarPorId(id);
        if (plantilla != null) {
            return ResponseEntity.ok(plantilla.getContenido());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar parcialmente una plantilla (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<PlantillaNotificacion> actualizarParcial(
            @PathVariable Integer id,
            @RequestBody PlantillaNotificacion cambios) {
        PlantillaNotificacion existente = service.buscarPorId(id);
        if (existente != null) {
            if (cambios.getNombre() != null) existente.setNombre(cambios.getNombre());
            if (cambios.getContenido() != null) existente.setContenido(cambios.getContenido());
            return ResponseEntity.ok(service.guardar(existente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una plantilla (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        PlantillaNotificacion existente = service.buscarPorId(id);
        if (existente != null) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}