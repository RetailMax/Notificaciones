package com.notificaciones.notificacionesFullstack.controller;

import com.notificaciones.notificacionesFullstack.model.NotificacionPago;
import com.notificaciones.notificacionesFullstack.service.NotificacionPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones/pago")
public class NotificacionPagoController {

    @Autowired
    private NotificacionPagoService notificacionPagoService;

    @PostMapping
    public ResponseEntity<NotificacionPago> crear(@RequestBody NotificacionPago notificacion) {
        NotificacionPago guardada = notificacionPagoService.guardar(notificacion);
        return ResponseEntity.ok(guardada);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionPago>> listar() {
        List<NotificacionPago> lista = notificacionPagoService.listarTodas();
        return ResponseEntity.ok(lista);
    }
}