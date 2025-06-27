package com.retailmax.notificaciones.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.retailmax.notificaciones.model.Promocion;
import com.retailmax.notificaciones.service.PromocionService;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping
    public List<Promocion> listar() {
        return promocionService.listarTodas();
    }

    @PostMapping
    public Promocion crear(@RequestBody Promocion promocion) {
        return promocionService.save(promocion);
    }
    @GetMapping("/{id}")
public Promocion buscarPorId(@PathVariable Long id) {
    return promocionService.buscarPorId(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
}
}