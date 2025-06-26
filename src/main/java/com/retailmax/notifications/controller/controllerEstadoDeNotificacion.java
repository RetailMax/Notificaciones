package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import com.retailmax.notifications.service.serviceEstadoDeNotificacion;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificaciones")
public class controllerEstadoDeNotificacion {

    private final serviceEstadoDeNotificacion service;

    public controllerEstadoDeNotificacion(serviceEstadoDeNotificacion service) {
        this.service = service;
    }

    @Operation(summary = "Registrar una nueva notificación")
    @PostMapping
    public EntityModel<EstadoDeNotificacion> registrar(@RequestBody EstadoDeNotificacion notificacion) {
        EstadoDeNotificacion guardada = service.guardar(notificacion);
        return EntityModel.of(guardada,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerEstadoDeNotificacion.class)
                        .buscarPorId(guardada.getNotificacionId())).withSelfRel());
    }

    @Operation(summary = "Obtener todas las notificaciones")
    @GetMapping
    public List<EntityModel<EstadoDeNotificacion>> obtenerTodas() {
        return service.obtenerTodas().stream()
                .map(n -> EntityModel.of(n,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerEstadoDeNotificacion.class)
                                .buscarPorId(n.getNotificacionId())).withSelfRel()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener notificaciones por cliente")
    @GetMapping("/cliente/{clienteId}")
    public List<EntityModel<EstadoDeNotificacion>> obtenerPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId).stream()
                .map(n -> EntityModel.of(n,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerEstadoDeNotificacion.class)
                                .buscarPorId(n.getNotificacionId())).withSelfRel()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar notificación por ID")
    @GetMapping("/{id}")
    public EntityModel<EstadoDeNotificacion> buscarPorId(@PathVariable Long id) {
        Optional<EstadoDeNotificacion> notificacion = service.buscarPorId(id);
        return notificacion
                .map(n -> EntityModel.of(n,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(controllerEstadoDeNotificacion.class)
                                .buscarPorId(id)).withSelfRel()))
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }
}