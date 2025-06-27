package com.retailmax.notifications.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoModelTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería crear un pedido válido con el constructor por defecto")
    void testConstructorPorDefecto() {
        Pedido pedido = new Pedido();
        assertNotNull(pedido);
        assertNull(pedido.getId());
        assertNull(pedido.getCodigoId());
        assertNull(pedido.getFechaPedido());
        assertNull(pedido.getEstadoPedido());
        assertNull(pedido.getMontoTotal());
        assertNull(pedido.getFechaEntrega());
        assertNull(pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería crear un pedido válido con el constructor completo")
    void testConstructorCompleto() {
        Date fechaPedido = new Date();
        Date fechaEntrega = new Date();
        Usuario usuario = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
        
        Pedido pedido = new Pedido(1L, "PED-001", fechaPedido, "PENDIENTE", "100.00", fechaEntrega, usuario);
        
        assertEquals(1L, pedido.getId());
        assertEquals("PED-001", pedido.getCodigoId());
        assertEquals(fechaPedido, pedido.getFechaPedido());
        assertEquals("PENDIENTE", pedido.getEstadoPedido());
        assertEquals("100.00", pedido.getMontoTotal());
        assertEquals(fechaEntrega, pedido.getFechaEntrega());
        assertEquals(usuario, pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería validar campos obligatorios")
    void testValidacionesBeanValidation() {
        Pedido pedido = new Pedido();
        pedido.setCodigoId(""); // Inválido - vacío
        pedido.setFechaPedido(null); // Inválido - null
        pedido.setEstadoPedido(""); // Inválido - vacío
        pedido.setMontoTotal(""); // Inválido - vacío
        
        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("codigoId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fechaPedido")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estadoPedido")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("montoTotal")));
    }

    @Test
    @DisplayName("Debería validar un pedido correcto")
    void testValidacionesPedidoCorrecto() {
        Pedido pedido = new Pedido();
        pedido.setCodigoId("PED-001");
        pedido.setFechaPedido(new Date());
        pedido.setEstadoPedido("PENDIENTE");
        pedido.setMontoTotal("100.00");
        
        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debería establecer y obtener todos los campos correctamente")
    void testSettersAndGetters() {
        Pedido pedido = new Pedido();
        Date fechaPedido = new Date();
        Date fechaEntrega = new Date();
        Usuario usuario = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
        
        pedido.setId(1L);
        pedido.setCodigoId("PED-001");
        pedido.setFechaPedido(fechaPedido);
        pedido.setEstadoPedido("ENTREGADO");
        pedido.setMontoTotal("150.50");
        pedido.setFechaEntrega(fechaEntrega);
        pedido.setUsuario(usuario);
        
        assertEquals(1L, pedido.getId());
        assertEquals("PED-001", pedido.getCodigoId());
        assertEquals(fechaPedido, pedido.getFechaPedido());
        assertEquals("ENTREGADO", pedido.getEstadoPedido());
        assertEquals("150.50", pedido.getMontoTotal());
        assertEquals(fechaEntrega, pedido.getFechaEntrega());
        assertEquals(usuario, pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería tener toString, equals y hashCode funcionales")
    void testToStringEqualsHashCode() {
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        pedido1.setCodigoId("PED-001");
        pedido1.setFechaPedido(new Date());
        pedido1.setEstadoPedido("PENDIENTE");
        pedido1.setMontoTotal("100.00");
        
        Pedido pedido2 = new Pedido();
        pedido2.setId(1L);
        pedido2.setCodigoId("PED-001");
        pedido2.setFechaPedido(pedido1.getFechaPedido());
        pedido2.setEstadoPedido("PENDIENTE");
        pedido2.setMontoTotal("100.00");
        
        assertEquals(pedido1, pedido2);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
        assertTrue(pedido1.toString().contains("PED-001"));
        assertTrue(pedido1.toString().contains("PENDIENTE"));
    }

    @Test
    @DisplayName("Debería manejar relaciones con Usuario correctamente")
    void testRelacionConUsuario() {
        Usuario usuario = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        
        assertEquals(usuario, pedido.getUsuario());
        // La relación bidireccional no se establece automáticamente al usar setUsuario
        // Solo se establece cuando se usa el método addPedido del usuario
    }
} 