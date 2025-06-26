package com.retailmax.notifications.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioModelTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería crear un usuario válido con el constructor personalizado")
    void testConstructorPersonalizado() {
        Usuario usuario = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
        assertEquals("USR-001", usuario.getNroId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@email.com", usuario.getCorreoElectronico());
        assertEquals("ACTIVO", usuario.getEstado());
    }

    @Test
    @DisplayName("Debería validar campos obligatorios y formato de email")
    void testValidacionesBeanValidation() {
        Usuario usuario = new Usuario();
        usuario.setNroId(""); // Inválido
        usuario.setNombre(""); // Inválido
        usuario.setCorreoElectronico("correo-no-valido"); // Inválido
        usuario.setEstado("");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nroId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("correoElectronico")));
    }

    @Test
    @DisplayName("Debería agregar y remover pedidos correctamente")
    void testAddRemovePedido() {
        Usuario usuario = new Usuario("USR-002", "Ana Gómez", "ana@email.com");
        Pedido pedido = new Pedido();
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
        Usuario usuario1 = new Usuario("USR-003", "Carlos Ruiz", "carlos@email.com");
        Usuario usuario2 = new Usuario("USR-003", "Carlos Ruiz", "carlos@email.com");
        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
        assertTrue(usuario1.toString().contains("Carlos Ruiz"));
    }
}
