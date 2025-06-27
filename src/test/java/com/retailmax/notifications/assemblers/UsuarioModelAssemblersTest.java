package com.retailmax.notifications.assemblers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.retailmax.notifications.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class UsuarioModelAssemblersTest {

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
    @DisplayName("Debería convertir usuario a EntityModel con enlaces HATEOAS")
    void testToModel() {
        // When
        EntityModel<Usuario> entityModel = assembler.toModel(usuario);

        // Then
        assertNotNull(entityModel);
        assertEquals(usuario, entityModel.getContent());
        
        // Verificar que tiene enlaces
        assertTrue(entityModel.hasLinks());
        
        // Verificar enlaces específicos
        assertTrue(entityModel.hasLink("self"));
        assertTrue(entityModel.hasLink("usuarios"));
        assertTrue(entityModel.hasLink("actualizar"));
        assertTrue(entityModel.hasLink("eliminar"));
        assertTrue(entityModel.hasLink("cambiarEstado"));
    }

    @Test
    @DisplayName("Debería crear EntityModel para usuario recién creado")
    void testToModelForCreation() {
        // When
        EntityModel<Usuario> entityModel = assembler.toModelForCreation(usuario);

        // Then
        assertNotNull(entityModel);
        assertEquals(usuario, entityModel.getContent());
        
        // Verificar que tiene enlaces
        assertTrue(entityModel.hasLinks());
        
        // Verificar enlaces específicos para creación
        assertTrue(entityModel.hasLink("self"));
        assertTrue(entityModel.hasLink("usuarios"));
        assertTrue(entityModel.hasLink("actualizar"));
        assertTrue(entityModel.hasLink("eliminar"));
        
        // No debería tener enlace de cambiarEstado en creación
        assertFalse(entityModel.hasLink("cambiarEstado"));
    }

    @Test
    @DisplayName("Debería crear EntityModel para usuario actualizado con estado ACTIVO")
    void testToModelForUpdateActivo() {
        // Given
        usuario.setEstado("ACTIVO");

        // When
        EntityModel<Usuario> entityModel = assembler.toModelForUpdate(usuario);

        // Then
        assertNotNull(entityModel);
        assertEquals(usuario, entityModel.getContent());
        
        // Verificar que tiene enlaces
        assertTrue(entityModel.hasLinks());
        
        // Verificar enlaces específicos para actualización
        assertTrue(entityModel.hasLink("self"));
        assertTrue(entityModel.hasLink("usuarios"));
        assertTrue(entityModel.hasLink("actualizar"));
        assertTrue(entityModel.hasLink("eliminar"));
        assertTrue(entityModel.hasLink("cambiarEstado"));
        
        // Verificar que el enlace cambiarEstado apunta a INACTIVO cuando el estado es ACTIVO
        Link cambiarEstadoLink = entityModel.getLink("cambiarEstado").orElse(null);
        assertNotNull(cambiarEstadoLink);
        assertTrue(cambiarEstadoLink.getHref().contains("INACTIVO"));
    }

    @Test
    @DisplayName("Debería crear EntityModel para usuario actualizado con estado INACTIVO")
    void testToModelForUpdateInactivo() {
        // Given
        usuario.setEstado("INACTIVO");

        // When
        EntityModel<Usuario> entityModel = assembler.toModelForUpdate(usuario);

        // Then
        assertNotNull(entityModel);
        assertEquals(usuario, entityModel.getContent());
        
        // Verificar que tiene enlaces
        assertTrue(entityModel.hasLinks());
        
        // Verificar enlaces específicos para actualización
        assertTrue(entityModel.hasLink("self"));
        assertTrue(entityModel.hasLink("usuarios"));
        assertTrue(entityModel.hasLink("actualizar"));
        assertTrue(entityModel.hasLink("eliminar"));
        assertTrue(entityModel.hasLink("cambiarEstado"));
        
        // Verificar que el enlace cambiarEstado apunta a ACTIVO cuando el estado es INACTIVO
        Link cambiarEstadoLink = entityModel.getLink("cambiarEstado").orElse(null);
        assertNotNull(cambiarEstadoLink);
        assertTrue(cambiarEstadoLink.getHref().contains("ACTIVO"));
    }

    @Test
    @DisplayName("Debería manejar usuario sin ID correctamente")
    void testToModelWithoutId() {
        // Given
        Usuario usuarioSinId = new Usuario("USR-002", "Ana Gómez", "ana@email.com");
        // No establecer ID

        // When
        EntityModel<Usuario> entityModel = assembler.toModel(usuarioSinId);

        // Then
        assertNotNull(entityModel);
        assertEquals(usuarioSinId, entityModel.getContent());
        // HATEOAS puede manejar IDs null, por lo que no debería lanzar excepción
        assertTrue(entityModel.hasLinks());
    }

    @Test
    @DisplayName("Debería manejar usuario null correctamente")
    void testToModelWithNullUsuario() {
        // When & Then
        assertThrows(Exception.class, () -> assembler.toModel(null));
    }

    @Test
    @DisplayName("Debería generar enlaces con URLs correctas")
    void testToModelWithCorrectUrls() {
        // When
        EntityModel<Usuario> entityModel = assembler.toModel(usuario);

        // Then
        Link selfLink = entityModel.getLink("self").orElse(null);
        assertNotNull(selfLink);
        assertTrue(selfLink.getHref().contains("/api/v1/usuarios/1"));
        
        Link usuariosLink = entityModel.getLink("usuarios").orElse(null);
        assertNotNull(usuariosLink);
        assertTrue(usuariosLink.getHref().contains("/api/v1/usuarios"));
    }
} 