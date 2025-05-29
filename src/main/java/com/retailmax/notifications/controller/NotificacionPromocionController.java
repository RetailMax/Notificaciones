package com.retailmax.notifications.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.retailmax.notifications.model.NotificacionPromocion;
import com.retailmax.notifications.service.NotificacionPromocionService; // <-- Importa el servicio correcto
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/NotificacionPromocion")
public class NotificacionPromocionController {

    @Autowired
    private NotificacionPromocionService notificacionPromocionService; // <-- Inyecta el servicio correcto

    @GetMapping
    public ResponseEntity<List<NotificacionPromocion>> listar() {
        List<NotificacionPromocion> notificaciones = notificacionPromocionService.findAll();
        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificaciones);
    }

    // Crear una nueva notificación de promoción personalizada
    @PostMapping
    public ResponseEntity<NotificacionPromocion> crear(@RequestBody NotificacionPromocion notificacion) {
        NotificacionPromocion nueva = notificacionPromocionService.save(notificacion);
        return ResponseEntity.ok(nueva);
    }

    // Listar notificaciones de promoción por cliente
   
}

