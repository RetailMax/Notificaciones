package com.retailmax.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.service.UsuarioService;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService servicio;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = servicio.buscarTodas();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<Usuario> enviar(@RequestBody Usuario notificacion) {
        return ResponseEntity.ok(servicio.guardar(notificacion));
    }
}

