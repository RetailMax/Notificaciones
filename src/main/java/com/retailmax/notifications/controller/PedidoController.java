package com.retailmax.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.service.PedidoService;


@RestController
@RequestMapping("/api/v2/pedidos")

public class PedidoController {

    @Autowired
    private PedidoService servicio;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> lista = servicio.buscarTodas();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<Pedido> enviar(@RequestBody Pedido notificacion) {
        return ResponseEntity.ok(servicio.guardar(notificacion));
    }

}
