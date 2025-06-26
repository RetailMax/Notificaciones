package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.PreferenciasNotificacion;
import com.retailmax.notifications.service.PreferenciasNotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferenciasNotificacion")
public class PreferenciasNotificacionController {
    private final PreferenciasNotificacionService service;

    public PreferenciasNotificacionController(PreferenciasNotificacionService service) {
        this.service = service;
    }

    // para obtener las preferencias    Atento a los otros grupos para cambiar este punto
    @GetMapping("/{userId}")
    public ResponseEntity<PreferenciasNotificacion> get(@PathVariable Long userId) {
        return ResponseEntity.ok(service.obtenerPorUserId(userId));
    }

    // Para actualizar las preferencias
    @PutMapping("/{userId}")
    public ResponseEntity<?> update(
            @PathVariable Long userId,
            @RequestBody PreferenciasNotificacion preferencias) {

        // Se necesita Validar al menos email o sms este marcado
        if (!preferencias.isEmail() && !preferencias.isSms()) {
            return ResponseEntity.badRequest().body("Debe seleccionar al menos un canal: email o sms.");
        }
        return ResponseEntity.ok(service.actualizar(userId, preferencias));
    }
}