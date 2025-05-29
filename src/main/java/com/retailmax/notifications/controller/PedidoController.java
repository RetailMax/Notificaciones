package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.PedidoNotificacion;
import com.retailmax.notifications.service.PedidoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoController {


    @Autowired
    private PedidoNotificacionService pedidoNotificacionService;

    @GetMapping
    public ResponseEntity<List<PedidoNotificacion>> listar() {
        List<PedidoNotificacion> lista = pedidoNotificacionService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoNotificacion> obtenerPorId(@PathVariable Integer id) {
        Optional<PedidoNotificacion> pedido = pedidoNotificacionService.findById(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

     @PostMapping
    public ResponseEntity<PedidoNotificacion> crear(@RequestBody PedidoNotificacion pedido) {
        PedidoNotificacion nuevo = pedidoNotificacionService.save(pedido);
        return ResponseEntity.ok(nuevo);
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pedidoNotificacionService.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
}


