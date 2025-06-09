package com.retailmax.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.retailmax.notifications.model.NotificacionConfirmacionPedido;
import com.retailmax.notifications.service.NotificacionConfirmacionPedidoService;

@RestController
@RequestMapping("/api/v1/notificaciones/confirmacion-pedido")
public class NotificacionConfirmacionPedidoController {

    @Autowired
    private NotificacionConfirmacionPedidoService servicio;

    @GetMapping
    public ResponseEntity<List<NotificacionConfirmacionPedido>> listar() {
        List<NotificacionConfirmacionPedido> lista = servicio.buscarTodas();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<NotificacionConfirmacionPedido> enviar(@RequestBody NotificacionConfirmacionPedido notificacion) {
        return ResponseEntity.ok(servicio.enviar(notificacion));
    }
}




