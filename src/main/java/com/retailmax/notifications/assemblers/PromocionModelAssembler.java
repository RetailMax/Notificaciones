package com.retailmax.notifications.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.retailmax.notifications.controller.PromocionControllerV2;
import com.retailmax.notifications.model.Promocion;

@Component
public class PromocionModelAssembler implements RepresentationModelAssembler<Promocion, EntityModel<Promocion>> {

    @Override
    public EntityModel<Promocion> toModel(Promocion promocion) {
        return EntityModel.of(promocion,
                linkTo(methodOn(PromocionControllerV2.class).getPromocionById(promocion.getId())).withSelfRel(),
                linkTo(methodOn(PromocionControllerV2.class).getAllPromociones()).withRel("carreras"));
    }
}
