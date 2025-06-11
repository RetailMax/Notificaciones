package com.retailmax.notifications.controller;



    
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.retailmax.notifications.model.NotificacionAlerta;
import com.retailmax.notifications.service.NotificacionAlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController//indica que esta clase es un controlador REST devuelve datos en formato JSON
@RequestMapping("/api/v1/NotificacionAlerta")// es por donde se empiezan con ruta base
public class NotificacionAlertaController {

    @Autowired
    private NotificacionAlertaService notificacionAlertaService;//contiene la logica de negocio

    
    
    @GetMapping()
    public ResponseEntity<List<NotificacionAlerta>>Listar() {
   List<NotificacionAlerta> notificaciones = notificacionAlertaService.findAll();
    if(notificaciones.isEmpty()) {
        return ResponseEntity.noContent().build();//devuelve una lista de alertas
   
    }
    return ResponseEntity.ok(notificaciones);
    }
 @PostMapping()
    public ResponseEntity<NotificacionAlerta> crear(@RequestBody NotificacionAlerta notificacionAlerta) {
        NotificacionAlerta guardada = notificacionAlertaService.save(notificacionAlerta);
        return ResponseEntity.ok(guardada);//crea una nueva alerta 
    }
}


