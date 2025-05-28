package com.notificaciones.notificacionesFullstack.controller;

import com.notificaciones.notificacionesFullstack.model.PreferenciaNotificacion;
import com.notificaciones.notificacionesFullstack.service.PreferenciaNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/preferencias")
public class PreferenciaNotificacionController {

    @Autowired
    private PreferenciaNotificacionService service;

    @PostMapping
    public ResponseEntity<PreferenciaNotificacion> guardar(@RequestBody PreferenciaNotificacion pref) {
        return ResponseEntity.ok(service.guardar(pref));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<PreferenciaNotificacion> obtener(@PathVariable Integer idUsuario) {
        return service.buscarPorId(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idUsuario}")
        public ResponseEntity<Void> eliminar(@PathVariable Integer idUsuario) {
        service.eliminar(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<PreferenciaNotificacion> actualizarParcial(
            @PathVariable Integer idUsuario,
            @RequestBody PreferenciaNotificacion cambios) {
        PreferenciaNotificacion actualizada = service.actualizarParcial(idUsuario, cambios);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
}
}
