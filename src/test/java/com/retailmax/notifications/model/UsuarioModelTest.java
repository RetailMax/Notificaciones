package com.retailmax.notifications.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del modelo Usuario")
public class UsuarioModelTest {

    private final Validator validator;
    private Usuario usuario;

    public UsuarioModelTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNroId("12345678");
        usuario.setNombre("Juan Pérez");
        usuario.setCorreoElectronico("juan.perez@email.com");
        usuario.setEstado("ACTIVO");
    }

    @Test
    @DisplayName("Debería crear un Usuario correctamente con el constructor por defecto")
    void testConstructorPorDefecto() {
        Usuario nuevoUsuario = new Usuario();
        assertNotNull(nuevoUsuario);
        assertNotNull(nuevoUsuario.getPedidos());
        assertTrue(nuevoUsuario.getPedidos().isEmpty());
    }

    @Test
    @DisplayName("Debería crear un Usuario correctamente con el constructor con parámetros")
    void testConstructorConParametros() {
        Usuario nuevoUsuario = new Usuario("87654321", "María García", "maria.garcia@email.com");
        assertEquals("87654321", nuevoUsuario.getNroId());
        assertEquals("María García", nuevoUsuario.getNombre());
        assertEquals("maria.garcia@email.com", nuevoUsuario.getCorreoElectronico());
        assertEquals("ACTIVO", nuevoUsuario.getEstado()); // Valor por defecto
    }

    @Test
    @DisplayName("Debería validar correctamente los campos obligatorios")
    void testValidacionesBeanValidation() {
        Usuario usuarioInvalido = new Usuario();
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuarioInvalido);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nroId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("correoElectronico")));
    }

    @Test
    @DisplayName("Debería validar que el nroId tenga entre 5 y 20 caracteres")
    void testValidacionNroIdLongitud() {
        // NroId muy corto
        usuario.setNroId("123");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nroId")));

        // NroId muy largo
        usuario.setNroId("123456789012345678901");
        violations = validator.validate(usuario);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nroId")));

        // NroId válido
        usuario.setNroId("12345678");
        violations = validator.validate(usuario);
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nroId")));
    }

    @Test
    @DisplayName("Debería validar que el nombre tenga entre 2 y 50 caracteres")
    void testValidacionNombreLongitud() {
        // Nombre muy corto
        usuario.setNombre("A");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));

        // Nombre muy largo
        usuario.setNombre("Este es un nombre muy largo que excede los cincuenta caracteres permitidos");
        violations = validator.validate(usuario);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));

        // Nombre válido
        usuario.setNombre("Juan Pérez");
        violations = validator.validate(usuario);
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería validar el formato del correo electrónico")
    void testValidacionCorreoElectronico() {
        // Correo inválido
        usuario.setCorreoElectronico("correo-invalido");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("correoElectronico")));

        // Correo válido
        usuario.setCorreoElectronico("juan.perez@email.com");
        violations = validator.validate(usuario);
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("correoElectronico")));
    }

    @Test
    @DisplayName("Debería agregar pedidos correctamente")
    void testAddPedido() {
        Pedido pedido = new Pedido(1L, new BigDecimal("100.00"), LocalDateTime.now());
        
        usuario.addPedido(pedido);
        
        assertEquals(1, usuario.getPedidos().size());
        assertTrue(usuario.getPedidos().contains(pedido));
        assertEquals(usuario, pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería remover pedidos correctamente")
    void testRemovePedido() {
        Pedido pedido = new Pedido(1L, new BigDecimal("100.00"), LocalDateTime.now());
        
        usuario.addPedido(pedido);
        assertEquals(1, usuario.getPedidos().size());
        
        usuario.removePedido(pedido);
        
        assertEquals(0, usuario.getPedidos().size());
        assertFalse(usuario.getPedidos().contains(pedido));
        assertNull(pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería manejar múltiples pedidos correctamente")
    void testMultiplePedidos() {
        Pedido pedido1 = new Pedido(1L, new BigDecimal("100.00"), LocalDateTime.now());
        Pedido pedido2 = new Pedido(2L, new BigDecimal("200.00"), LocalDateTime.now());
        
        usuario.addPedido(pedido1);
        usuario.addPedido(pedido2);
        
        assertEquals(2, usuario.getPedidos().size());
        assertTrue(usuario.getPedidos().contains(pedido1));
        assertTrue(usuario.getPedidos().contains(pedido2));
        assertEquals(usuario, pedido1.getUsuario());
        assertEquals(usuario, pedido2.getUsuario());
        
        usuario.removePedido(pedido1);
        assertEquals(1, usuario.getPedidos().size());
        assertFalse(usuario.getPedidos().contains(pedido1));
        assertTrue(usuario.getPedidos().contains(pedido2));
        assertNull(pedido1.getUsuario());
        assertEquals(usuario, pedido2.getUsuario());
    }

    @Test
    @DisplayName("Debería establecer el estado por defecto correctamente")
    void testEstadoPorDefecto() {
        Usuario nuevoUsuario = new Usuario("12345678", "Test User", "test@email.com");
        assertEquals("ACTIVO", nuevoUsuario.getEstado());
    }

    @Test
    @DisplayName("Debería funcionar correctamente el método toString")
    void testToString() {
        String str = usuario.toString();
        assertTrue(str.contains("id"));
        assertTrue(str.contains("nroId"));
        assertTrue(str.contains("nombre"));
        assertTrue(str.contains("correoElectronico"));
    }
} 