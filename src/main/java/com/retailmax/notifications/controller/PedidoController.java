package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.repository.PedidoRepository;
import com.retailmax.notifications.assemblers.PedidoModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoModelAssembler assembler;

    public PedidoController(PedidoRepository pedidoRepository, PedidoModelAssembler assembler) {
        this.pedidoRepository = pedidoRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Pedido>> getAllPedidos() {
        List<EntityModel<Pedido>> pedidos = pedidoRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos);
    }

    @GetMapping("/{id}")
    public EntityModel<Pedido> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return assembler.toModel(pedido);
    }
} 