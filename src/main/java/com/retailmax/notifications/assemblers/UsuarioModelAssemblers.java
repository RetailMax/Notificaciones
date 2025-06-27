package com.retailmax.notifications.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import io.swagger.v3.oas.annotations.media.Schema;

import com.retailmax.notifications.controller.UsuarioController;
import com.retailmax.notifications.model.Usuario;

/**
 * Assembler para convertir entidades Usuario en modelos HATEOAS
 * Proporciona enlaces automáticos a los endpoints relacionados
 */
@Component
@Schema(description = "Assembler para modelos de Usuario con enlaces HATEOAS")
public class UsuarioModelAssemblers implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    /**
     * Convierte una entidad Usuario en un EntityModel con enlaces HATEOAS
     * 
     * @param usuario La entidad Usuario a convertir
     * @return EntityModel con enlaces a endpoints relacionados
     */
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                // Enlace a sí mismo (self)
                linkTo(methodOn(UsuarioController.class).obtenerPorId(usuario.getId())).withSelfRel(),
                // Enlace a la colección de usuarios
                linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"),
                // Enlace para actualizar el usuario
                linkTo(methodOn(UsuarioController.class).actualizar(usuario.getId(), usuario)).withRel("actualizar"),
                // Enlace para eliminar el usuario
                linkTo(methodOn(UsuarioController.class).eliminar(usuario.getId())).withRel("eliminar"),
                // Enlace para cambiar estado
                linkTo(methodOn(UsuarioController.class).cambiarEstado(usuario.getId(), "ACTIVO")).withRel("cambiarEstado"));
    }

    /**
     * Crea un EntityModel para un usuario recién creado
     * 
     * @param usuario El usuario recién creado
     * @return EntityModel con enlaces apropiados para un recurso nuevo
     */
    public EntityModel<Usuario> toModelForCreation(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).obtenerPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"),
                linkTo(methodOn(UsuarioController.class).actualizar(usuario.getId(), usuario)).withRel("actualizar"),
                linkTo(methodOn(UsuarioController.class).eliminar(usuario.getId())).withRel("eliminar"));
    }

    /**
     * Crea un EntityModel para un usuario actualizado
     * 
     * @param usuario El usuario actualizado
     * @return EntityModel con enlaces apropiados para un recurso actualizado
     */
    public EntityModel<Usuario> toModelForUpdate(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).obtenerPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios"),
                linkTo(methodOn(UsuarioController.class).actualizar(usuario.getId(), usuario)).withRel("actualizar"),
                linkTo(methodOn(UsuarioController.class).eliminar(usuario.getId())).withRel("eliminar"),
                linkTo(methodOn(UsuarioController.class).cambiarEstado(usuario.getId(), 
                    "ACTIVO".equals(usuario.getEstado()) ? "INACTIVO" : "ACTIVO")).withRel("cambiarEstado"));
    }
}