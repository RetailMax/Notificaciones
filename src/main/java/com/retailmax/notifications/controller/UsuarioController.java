package com.retailmax.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.service.UsuarioService;
import com.retailmax.notifications.assemblers.UsuarioModelAssemblers;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssemblers assembler;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listar() {
        List<Usuario> lista = usuarioService.findAll();
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<EntityModel<Usuario>> usuarios = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Usuario>> collection = CollectionModel.of(usuarios);
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene un usuario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> obtenerPorId(
            @Parameter(description = "ID del usuario a buscar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(assembler.toModel(usuario));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    public ResponseEntity<EntityModel<Usuario>> crear(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @Valid @RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.save(usuario);
        return ResponseEntity.created(
            org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                .linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                    .methodOn(UsuarioController.class).obtenerPorId(usuarioCreado.getId()))
                .toUri())
            .body(assembler.toModelForCreation(usuarioCreado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos o duplicados")
    })
    public ResponseEntity<EntityModel<Usuario>> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del usuario", required = true)
            @Valid @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.update(id, usuario);
            return ResponseEntity.ok(assembler.toModelForUpdate(actualizado));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (org.springframework.dao.DuplicateKeyException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de usuario", description = "Cambia el estado de un usuario (ACTIVO/INACTIVO)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado del usuario cambiado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> cambiarEstado(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del usuario", required = true, example = "ACTIVO")
            @RequestParam String estado) {
        try {
            Usuario usuario = usuarioService.cambiarEstado(id, estado);
            return ResponseEntity.ok(assembler.toModelForUpdate(usuario));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

