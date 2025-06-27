package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.service.PreferenciaDeNotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Preferencias obtenidas correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{clienteId}")
    public CollectionModel<EntityModel<PreferenciaDeNotificacion>> obtenerPreferenciasPorCliente(@PathVariable Long clienteId) {
        List<EntityModel<PreferenciaDeNotificacion>> preferencias = service.obtenerPorCliente(clienteId)
            .stream()
            .map(pref -> EntityModel.of(pref,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PreferenciaDeNotificacionController.class)
                    .obtenerPreferenciasPorCliente(clienteId)).withSelfRel()))
            .collect(Collectors.toList());

        return CollectionModel.of(preferencias,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PreferenciaDeNotificacionController.class)
                .obtenerPreferenciasPorCliente(clienteId)).withSelfRel());
    }

    @Operation(summary = "Actualizar preferencias de notificación por cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Preferencias actualizadas correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{clienteId}")
    public void actualizarPreferencias(@PathVariable Long clienteId,
                                       @RequestBody @Valid List<PreferenciaDeNotificacion> nuevasPreferencias) {
        boolean alMenosUnaHabilitada = nuevasPreferencias.stream()
            .anyMatch(PreferenciaDeNotificacion::getHabilitado);

        if (!alMenosUnaHabilitada) {
            nuevasPreferencias.add(new PreferenciaDeNotificacion(clienteId, 1L, true)); // Canal 1 = email por defecto
        }

        service.actualizarPreferencias(clienteId, nuevasPreferencias);
    }
}