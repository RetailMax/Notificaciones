package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.PreferenciasDeNotificacion;
import com.retailmax.notifications.service.ServicePreferenciasDeNotificacion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferencias")
public class ControllerPreferenciasDeNotificacion {

    private final ServicePreferenciasDeNotificacion service;

    public ControllerPreferenciasDeNotificacion(ServicePreferenciasDeNotificacion service) {
        this.service = service;
    }

    @GetMapping("/{clienteId}")
    public List<PreferenciasDeNotificacion> getPreferencias(@PathVariable Long clienteId) {
        List<PreferenciasDeNotificacion> prefs = service.obtenerPorCliente(clienteId);
        if (prefs.isEmpty()) {
            service.crearPorDefecto(clienteId);
            prefs = service.obtenerPorCliente(clienteId);
        }
        return prefs;
    }

    @PutMapping("/{clienteId}")
    public void updatePreferencias(@PathVariable Long clienteId, @RequestBody List<PreferenciasDeNotificacion> nuevas) {
        boolean alguno = nuevas.stream().anyMatch(PreferenciasDeNotificacion::getHabilitado);
        if (!alguno) {
            nuevas.add(new PreferenciasDeNotificacion(clienteId, 1L, true)); // 1 = email por defecto
        }
        service.actualizarPreferencias(clienteId, nuevas);
    }
}
