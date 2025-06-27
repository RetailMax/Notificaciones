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

    @Test
    void testConstructorPorDefectoYSettersYGetters() {
        Pedido pedido = new Pedido();
        pedido.setPedidoId(10L);
        pedido.setClienteId(100L);
        LocalDateTime fechaPedido = LocalDateTime.of(2024, 6, 26, 12, 0);
        pedido.setFechaPedido(fechaPedido);
        pedido.setEstadoPedido(EstadoPedido.ENTREGADO);
        pedido.setTotal(new BigDecimal("123.45"));
        LocalDateTime fechaEntrega = LocalDateTime.of(2024, 6, 27, 14, 0);
        pedido.setFechaEntregaEstimada(fechaEntrega);

        assertEquals(10L, pedido.getPedidoId());
        assertEquals(100L, pedido.getClienteId());
        assertEquals(fechaPedido, pedido.getFechaPedido());
        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstadoPedido());
        assertEquals(new BigDecimal("123.45"), pedido.getTotal());
        assertEquals(fechaEntrega, pedido.getFechaEntregaEstimada());
    }

   
    @Test
    void testRelacionConUsuario() {
        Pedido pedido = new Pedido();
        Usuario usuario = new Usuario();
        usuario.setId(5L);
        pedido.setUsuario(usuario);
        assertEquals(usuario, pedido.getUsuario());
    }

    @Test
    void testToString2() {
        Pedido pedido = new Pedido();
        pedido.setPedidoId(1L);
        pedido.setClienteId(2L);
        pedido.setFechaPedido(LocalDateTime.of(2024, 6, 26, 12, 0));
        pedido.setEstadoPedido(EstadoPedido.EN_CAMINO);
        pedido.setTotal(new BigDecimal("999.99"));
        pedido.setFechaEntregaEstimada(LocalDateTime.of(2024, 6, 27, 14, 0));
        Usuario usuario = new Usuario();
        usuario.setId(7L);
        pedido.setUsuario(usuario);
        String str = pedido.toString();
        assertTrue(str.contains("pedidoId=1"));
        assertTrue(str.contains("clienteId=2"));
        assertTrue(str.contains("estadoPedido=en camino") || str.contains("estadoPedido=EN_CAMINO") || str.contains("estadoPedido=EN CAMINO"));
        assertTrue(str.contains("999.99"));
        assertTrue(str.contains("usuario=Usuario") || str.contains("usuario=7") || str.contains("usuario="));
    }

    @Test
    void testToStringSinUsuario() {
        Pedido pedido = new Pedido();
        pedido.setPedidoId(1L);
        String str = pedido.toString();
        assertTrue(str.contains("usuario=null"));
    }
}
