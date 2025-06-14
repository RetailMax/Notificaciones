package com.retailmax.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.retailmax.notifications.assemblers.PromocionModelAssembler;
import com.retailmax.notifications.model.Promocion;
import com.retailmax.notifications.service.PromocionService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/promociones")
public class PromocionControllerV2 {

    @Autowired
    private PromocionService promocionService;

    @Autowired
    private PromocionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Promocion>> getAllPromociones() {
        List<EntityModel<Promocion>> promociones = promocionService.listarTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(promociones,
                linkTo(methodOn(PromocionControllerV2.class).getAllPromociones()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Promocion> getPromocionById(@PathVariable Long id) {
        Promocion promocion = promocionService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
        return assembler.toModel(promocion);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Promocion>> createPromocion(@RequestBody Promocion promocion) {
        Promocion newPromocion = promocionService.guardar(promocion);
        return ResponseEntity
                .created(linkTo(methodOn(PromocionControllerV2.class).getPromocionById(newPromocion.getId())).toUri())
                .body(assembler.toModel(newPromocion));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Promocion>> updatePromocion(@PathVariable Long id, @RequestBody Promocion promocion) {
        promocion.setId(id);
        Promocion updatedPromocion = promocionService.guardar(promocion);
        return ResponseEntity.ok(assembler.toModel(updatedPromocion));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deletePromocion(@PathVariable Long id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}