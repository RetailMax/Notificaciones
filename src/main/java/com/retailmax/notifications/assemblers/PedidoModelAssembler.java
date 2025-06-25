package com.retailmax.notifications.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.retailmax.notifications.controller.PedidoController;
import com.retailmax.notifications.model.Pedido;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).obtenerPorId(pedido.getPedidoId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("pedidos"));
    }
} 