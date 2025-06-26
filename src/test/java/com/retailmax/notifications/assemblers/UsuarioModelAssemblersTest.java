package com.retailmax.notifications.assemblers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.retailmax.notifications.controller.UsuarioController;
import com.retailmax.notifications.model.Usuario;

@ExtendWith(MockitoExtension.class)
class UsuarioModelAssemblersTest {

    @InjectMocks
    private UsuarioModelAssemblers assembler;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Configurar el contexto de request para HATEOAS
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
        // Crear un usuario de prueba
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNroId("12345678");
        usuario.setNombre("Juan Pérez");
        usuario.setCorreoElectronico("juan.perez@email.com");
        usuario.setEstado("ACTIVO");
    }

    @Test
    void toModel_ShouldCreateEntityModelWithCorrectLinks() {
        // When
        EntityModel<Usuario> result = assembler.toModel(usuario);

        // Then
        assertNotNull(result);
        assertEquals(usuario, result.getContent());
        
        // Verificar que tiene los enlaces esperados
        assertTrue(result.hasLink("self"));
        assertTrue(result.hasLink("usuarios"));
        assertTrue(result.hasLink("actualizar"));
        assertTrue(result.hasLink("eliminar"));
        assertTrue(result.hasLink("cambiarEstado"));
        
        // Verificar que el enlace self apunta al usuario correcto
        Link selfLink = result.getLink("self").orElse(null);
        assertNotNull(selfLink);
        assertTrue(selfLink.getHref().contains("/api/v1/usuarios/1"));
    }

    @Test
    void toModelForCreation_ShouldCreateEntityModelWithCreationLinks() {
        // When
        EntityModel<Usuario> result = assembler.toModelForCreation(usuario);

        // Then
        assertNotNull(result);
        assertEquals(usuario, result.getContent());
        
        // Verificar que tiene los enlaces apropiados para creación
        assertTrue(result.hasLink("self"));
        assertTrue(result.hasLink("usuarios"));
        assertTrue(result.hasLink("actualizar"));
        assertTrue(result.hasLink("eliminar"));
        
        // No debería tener el enlace de cambiarEstado en creación
        assertFalse(result.hasLink("cambiarEstado"));
    }

    @Test
    void toModelForUpdate_ShouldCreateEntityModelWithUpdateLinks() {
        // When
        EntityModel<Usuario> result = assembler.toModelForUpdate(usuario);

        // Then
        assertNotNull(result);
        assertEquals(usuario, result.getContent());
        
        // Verificar que tiene todos los enlaces
        assertTrue(result.hasLink("self"));
        assertTrue(result.hasLink("usuarios"));
        assertTrue(result.hasLink("actualizar"));
        assertTrue(result.hasLink("eliminar"));
        assertTrue(result.hasLink("cambiarEstado"));
        
        // Verificar que el enlace cambiarEstado tiene el estado opuesto
        Link cambiarEstadoLink = result.getLink("cambiarEstado").orElse(null);
        assertNotNull(cambiarEstadoLink);
        assertTrue(cambiarEstadoLink.getHref().contains("estado=INACTIVO"));
    }

    @Test
    void toModelForUpdate_WithInactiveUser_ShouldHaveCorrectEstadoLink() {
        // Given
        usuario.setEstado("INACTIVO");

        // When
        EntityModel<Usuario> result = assembler.toModelForUpdate(usuario);

        // Then
        Link cambiarEstadoLink = result.getLink("cambiarEstado").orElse(null);
        assertNotNull(cambiarEstadoLink);
        assertTrue(cambiarEstadoLink.getHref().contains("estado=ACTIVO"));
    }

    @Test
    void toModel_WithNullUsuario_ShouldThrowException() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            assembler.toModel(null);
        });
    }

    @Test
    void toModel_WithUsuarioWithoutId_ShouldCreateLinksWithNullId() {
        // Given
        usuario.setId(null);

        // When
        EntityModel<Usuario> result = assembler.toModel(usuario);

        // Then
        assertNotNull(result);
        // Los enlaces se crearán pero con ID null, lo cual es válido para HATEOAS
        assertTrue(result.hasLink("self"));
    }
} 