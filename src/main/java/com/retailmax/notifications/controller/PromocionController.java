package com.retailmax.notifications.controller;


import com.retailmax.notifications.model.Promocion;
import com.retailmax.notifications.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    @Autowired
    private PromocionRepository promocionRepository;

    @GetMapping
    public List<Promocion> listar() {
        return promocionRepository.findAll();
    }

    @PostMapping
    public Promocion crear(@RequestBody Promocion promocion) {
        return promocionRepository.save(promocion);
    }
}