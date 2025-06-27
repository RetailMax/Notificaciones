package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.Notificacion;
import com.retailmax.notifications.service.NotificacionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @PostMapping
    public Notificacion registrar(@RequestBody Notificacion notificacion) {
        // Aquí puedes agregar lógica para notificar a soporte si resultado_envio es ERROR
        return service.registrar(notificacion);
    }

    @GetMapping
    public List<Notificacion> obtenerTodas() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Notificacion> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Notificacion> obtenerPorCliente(@PathVariable Long clienteId) {
        return service.obtenerPorCliente(clienteId);
    }
}