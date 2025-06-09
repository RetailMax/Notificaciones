package com.retailmax.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.retailmax.notifications.model.NotificacionActualizacionEstadoPedido;
import com.retailmax.notifications.service.NotificacionActualizacionEstadoPedidoService;


@RestController
@RequestMapping("/api/v1/notificaciones/actualizacion-estado-pedido")
public class NotificacionActualizacionEstadoPedidoController {

    @Autowired
    private NotificacionActualizacionEstadoPedidoService servicio;

    @GetMapping
    public ResponseEntity<List<NotificacionActualizacionEstadoPedido>> listar() {
        List<NotificacionActualizacionEstadoPedido> lista = servicio.buscarTodas();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<NotificacionActualizacionEstadoPedido> enviar(@RequestBody NotificacionActualizacionEstadoPedido notificacion) {
        return ResponseEntity.ok(servicio.enviar(notificacion));
    }
}

