package com.retailmax.notifications.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.retailmax.notificaciones.controller.PromocionControllerV2;
import com.retailmax.notificaciones.model.Promocion;

@Component
public class PromocionModelAssembler implements RepresentationModelAssembler<Promocion, EntityModel<Promocion>> {

    @Override
    @org.springframework.lang.NonNull
    public EntityModel<Promocion> toModel(@org.springframework.lang.NonNull Promocion promocion) {
        return EntityModel.of(promocion,
                linkTo(methodOn(PromocionControllerV2.class).getPromocionById(promocion.getId())).withSelfRel(),
                linkTo(methodOn(PromocionControllerV2.class).getAllPromociones()).withRel("promociones"));
    }
}
