package com.retailmax.notifications.model;

import org.junit.jupiter.api.BeforeEach;
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

    private Validator validator;
    private Pedido pedido;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        usuario = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
        pedido = new Pedido("PED-001", new BigDecimal("100.00"), LocalDateTime.now());
        pedido.setUsuario(usuario);
    }

    @Test
    @DisplayName("Debería crear un pedido válido con el constructor personalizado")
    void testConstructorPersonalizado() {
        assertEquals("PED-001", pedido.getCodigoId());
        assertEquals(new BigDecimal("100.00"), pedido.getMontoTotal());
        assertNotNull(pedido.getFechaPedido());
        assertEquals(Pedido.EstadoPedido.ENVIADO, pedido.getEstadoPedido());
        assertEquals(usuario, pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería validar campos obligatorios y restricciones")
    void testValidacionesBeanValidation() {
        Pedido pedidoInvalido = new Pedido();
        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedidoInvalido);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("codigoId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fechaPedido")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estadoPedido")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("montoTotal")));
    }

    @Test
    @DisplayName("Debería agregar y remover usuario correctamente")
    void testRelacionConUsuario() {
        Usuario otroUsuario = new Usuario("USR-002", "Ana Gómez", "ana@email.com");
        pedido.setUsuario(otroUsuario);
        assertEquals(otroUsuario, pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería establecer y obtener todos los campos correctamente")
    void testSettersAndGetters() {
        LocalDateTime fechaPedido = LocalDateTime.of(2024, 6, 26, 12, 0);
        LocalDateTime fechaEntrega = LocalDateTime.of(2024, 6, 27, 14, 0);
        pedido.setCodigoId("PED-002");
        pedido.setFechaPedido(fechaPedido);
        pedido.setEstadoPedido(Pedido.EstadoPedido.ENTREGADO);
        pedido.setMontoTotal(new BigDecimal("200.00"));
        pedido.setFechaEntrega(fechaEntrega);
        assertEquals("PED-002", pedido.getCodigoId());
        assertEquals(fechaPedido, pedido.getFechaPedido());
        assertEquals(Pedido.EstadoPedido.ENTREGADO, pedido.getEstadoPedido());
        assertEquals(new BigDecimal("200.00"), pedido.getMontoTotal());
        assertEquals(fechaEntrega, pedido.getFechaEntrega());
    }

    @Test
    @DisplayName("Debería tener toString, equals y hashCode funcionales")
    void testToStringEqualsHashCode() {
        Pedido pedido1 = new Pedido("PED-003", new BigDecimal("300.00"), LocalDateTime.now());
        Pedido pedido2 = new Pedido("PED-003", new BigDecimal("300.00"), LocalDateTime.now());
        assertNotNull(pedido1.toString());
        assertNotEquals(pedido1, pedido2); // Diferentes instancias
        assertNotEquals(pedido1.hashCode(), pedido2.hashCode());
    }
}
