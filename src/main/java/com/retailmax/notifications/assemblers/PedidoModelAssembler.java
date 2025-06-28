package com.retailmax.notifications.assemblers;

import com.retailmax.notifications.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {
    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
            linkTo(methodOn(com.retailmax.notifications.controller.PedidoController.class).getPedidoById(pedido.getId())).withSelfRel(),
            linkTo(methodOn(com.retailmax.notifications.controller.PedidoController.class).getAllPedidos()).withRel("pedidos")
        );
    }
} 