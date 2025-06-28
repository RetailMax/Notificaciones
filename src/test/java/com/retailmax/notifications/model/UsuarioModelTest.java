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

@DisplayName("Pruebas del modelo Usuario")
public class UsuarioModelTest {

    private Validator validator;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        usuario = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
    }

    @Test
    @DisplayName("Debería crear un usuario válido con el constructor personalizado")
    void testConstructorPersonalizado() {
        assertEquals("USR-001", usuario.getNroId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@email.com", usuario.getCorreoElectronico());
        assertEquals("ACTIVO", usuario.getEstado());
        assertNotNull(usuario.getPedidos());
        assertTrue(usuario.getPedidos().isEmpty());
    }

    @Test
    @DisplayName("Debería validar campos obligatorios y formato de email")
    void testValidacionesBeanValidation() {
        Usuario usuarioInvalido = new Usuario();
        usuarioInvalido.setNroId("");
        usuarioInvalido.setNombre("");
        usuarioInvalido.setCorreoElectronico("correo-no-valido");
        usuarioInvalido.setEstado("");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuarioInvalido);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nroId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("correoElectronico")));
    }

    @Test
    @DisplayName("Debería agregar y remover pedidos correctamente")
    void testAddRemovePedido() {
        Pedido pedido = new Pedido("PED-001", new BigDecimal("100.00"), LocalDateTime.now());
        usuario.addPedido(pedido);
        assertTrue(usuario.getPedidos().contains(pedido));
        assertEquals(usuario, pedido.getUsuario());
        usuario.removePedido(pedido);
        assertFalse(usuario.getPedidos().contains(pedido));
        assertNull(pedido.getUsuario());
    }

    @Test
    @DisplayName("Debería tener toString, equals y hashCode funcionales")
    void testToStringEqualsHashCode() {
        Usuario usuario1 = new Usuario("USR-002", "Ana Gómez", "ana@email.com");
        Usuario usuario2 = new Usuario("USR-003", "Carlos López", "carlos@email.com");
        assertNotNull(usuario1.toString());
        assertNotEquals(usuario1, usuario2); // Diferentes números de ID
        assertNotEquals(usuario1.hashCode(), usuario2.hashCode());
        
        // Usuarios con el mismo número de ID deberían ser iguales
        Usuario usuario3 = new Usuario("USR-002", "Ana Gómez", "ana@email.com");
        assertEquals(usuario1, usuario3);
        assertEquals(usuario1.hashCode(), usuario3.hashCode());
    }
}
