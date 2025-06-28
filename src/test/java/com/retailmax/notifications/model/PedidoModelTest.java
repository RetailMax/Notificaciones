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
        
        // Verificar que hay violaciones para los campos obligatorios
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("codigoId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("montoTotal")));
        
        // Verificar que no hay violaciones para campos con valores por defecto
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fechaPedido")));
        assertFalse(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("estadoPedido")));
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
        LocalDateTime fechaFija = LocalDateTime.of(2024, 6, 26, 12, 0);
        
        // Crear pedidos usando el constructor por defecto para evitar fechas automáticas
        Pedido pedido1 = new Pedido();
        pedido1.setCodigoId("PED-003");
        pedido1.setMontoTotal(new BigDecimal("300.00"));
        pedido1.setFechaPedido(fechaFija);
        pedido1.setFechaEntrega(fechaFija);
        pedido1.setEstadoPedido(Pedido.EstadoPedido.ENVIADO);
        
        Pedido pedido2 = new Pedido();
        pedido2.setCodigoId("PED-004");
        pedido2.setMontoTotal(new BigDecimal("400.00"));
        pedido2.setFechaPedido(fechaFija);
        pedido2.setFechaEntrega(fechaFija);
        pedido2.setEstadoPedido(Pedido.EstadoPedido.ENVIADO);
        
        assertNotNull(pedido1.toString());
        assertNotEquals(pedido1, pedido2); // Diferentes códigos de ID
        assertNotEquals(pedido1.hashCode(), pedido2.hashCode());
        
        // Pedidos con el mismo código deberían ser iguales
        Pedido pedido3 = new Pedido();
        pedido3.setCodigoId("PED-003");
        pedido3.setMontoTotal(new BigDecimal("300.00"));
        pedido3.setFechaPedido(fechaFija);
        pedido3.setFechaEntrega(fechaFija);
        pedido3.setEstadoPedido(Pedido.EstadoPedido.ENVIADO);
        
        assertEquals(pedido1, pedido3);
        assertEquals(pedido1.hashCode(), pedido3.hashCode());
    }
}
