package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.preferenciasDeNotificacion;
import com.retailmax.notifications.service.servicePreferenciasDeNotificacion;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/preferencias")
public class controllerPreferenciasDeNotificacion {

    private final servicePreferenciasDeNotificacion service;

    public controllerPreferenciasDeNotificacion(servicePreferenciasDeNotificacion service) {
        this.service = service;
    }

    @GetMapping("/{clienteId}")
    public List<preferenciasDeNotificacion> getPreferencias(@PathVariable Long clienteId) {
        List<preferenciasDeNotificacion> prefs = service.obtenerPorCliente(clienteId);
        if (prefs.isEmpty()) {
            service.crearPorDefecto(clienteId);
            prefs = service.obtenerPorCliente(clienteId);
        }
        return prefs;
    }

    @PutMapping("/{clienteId}")
    public void updatePreferencias(@PathVariable Long clienteId, @RequestBody List<preferenciasDeNotificacion> nuevas) {
        boolean alguno = nuevas.stream().anyMatch(preferenciasDeNotificacion::getHabilitado);
        if (!alguno) {
            nuevas.add(new preferenciasDeNotificacion(clienteId, 1L, true)); // 1 = email se asignara por defecto
        }
        service.actualizarPreferencias(clienteId, nuevas);
    }
}