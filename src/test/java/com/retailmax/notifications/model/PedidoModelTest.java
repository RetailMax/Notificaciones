package com.retailmax.notifications.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del modelo Pedido")
public class PedidoModelTest {

    private final Validator validator;

    public PedidoModelTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería crear un Pedido correctamente con el constructor por defecto")
    void testConstructorPorDefecto() {
        Pedido pedido = new Pedido();
        assertNotNull(pedido.getFechaPedido());
        assertEquals(EstadoPedido.ENVIADO, pedido.getEstadoPedido());
    }

    @Test
    @DisplayName("Debería crear un Pedido correctamente con el constructor con parámetros")
    void testConstructorConParametros() {
        LocalDateTime fechaEntrega = LocalDateTime.of(2024, 7, 1, 12, 0);
        Pedido pedido = new Pedido(123L, new BigDecimal("100.50"), fechaEntrega);
        assertEquals(123L, pedido.getClienteId());
        assertEquals(new BigDecimal("100.50"), pedido.getTotal());
        assertEquals(fechaEntrega, pedido.getFechaEntregaEstimada());
        assertNotNull(pedido.getFechaPedido());
        assertEquals(EstadoPedido.ENVIADO, pedido.getEstadoPedido());
    }

    @Test
    @DisplayName("Debería validar correctamente los campos obligatorios")
    void testValidacionesBeanValidation() {
        Pedido pedido = new Pedido();
        pedido.setClienteId(null);
        pedido.setTotal(null);
        pedido.setFechaPedido(null);
        pedido.setEstadoPedido(null);
        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("clienteId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("total")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fechaPedido")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estadoPedido")));
    }

    @Test
    @DisplayName("Debería validar que el total sea mayor a 0")
    void testValidacionTotalMayorACero() {
        Pedido pedido = new Pedido();
        pedido.setClienteId(1L);
        pedido.setTotal(new BigDecimal("0.00"));
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido(EstadoPedido.ENVIADO);
        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("total")));
    }

    @Test
    @DisplayName("Debería validar que el clienteId sea positivo")
    void testValidacionClienteIdPositivo() {
        Pedido pedido = new Pedido();
        pedido.setClienteId(-5L);
        pedido.setTotal(new BigDecimal("10.00"));
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido(EstadoPedido.ENVIADO);
        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("clienteId")));
    }

    @Test
    @DisplayName("Debería funcionar correctamente el método toString")
    void testToString() {
        Pedido pedido = new Pedido(1L, new BigDecimal("10.00"), LocalDateTime.now());
        String str = pedido.toString();
        assertTrue(str.contains("pedidoId"));
        assertTrue(str.contains("clienteId"));
        assertTrue(str.contains("total"));
    }
}
