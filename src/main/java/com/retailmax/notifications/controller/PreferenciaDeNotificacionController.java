package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.service.PreferenciaDeNotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciaDeNotificacionController {

    private final PreferenciaDeNotificacionService service;

    public PreferenciaDeNotificacionController(PreferenciaDeNotificacionService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener preferencias de notificación por cliente")
    @GetMapping("/{clienteId}")
    public CollectionModel<EntityModel<PreferenciaDeNotificacion>> getPreferencias(@PathVariable Long clienteId) {
        List<EntityModel<PreferenciaDeNotificacion>> prefs = service.obtenerPorCliente(clienteId)
            .stream()
            .map(pref -> EntityModel.of(pref,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PreferenciaDeNotificacionController.class)
                    .getPreferencias(clienteId)).withSelfRel()))
            .collect(Collectors.toList());
        return CollectionModel.of(prefs,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PreferenciaDeNotificacionController.class)
                .getPreferencias(clienteId)).withSelfRel());
    }

    @Operation(summary = "Actualizar preferencias de notificación por cliente")
    @PutMapping("/{clienteId}")
    public void updatePreferencias(@PathVariable Long clienteId, @RequestBody List<PreferenciaDeNotificacion> nuevas) {
        boolean alguno = nuevas.stream().anyMatch(PreferenciaDeNotificacion::getHabilitado);
        if (!alguno) {
            nuevas.add(new PreferenciaDeNotificacion(clienteId, 1L, true)); // 1 = email por defecto
        }
        service.actualizarPreferencias(clienteId, nuevas);
    }
}