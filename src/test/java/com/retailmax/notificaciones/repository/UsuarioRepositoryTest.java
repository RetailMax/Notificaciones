package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests básicos para UsuarioRepository")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Guardar y buscar usuario por nroId")
    void testGuardarYBuscarPorNroId() {
        Usuario usuario = new Usuario("USR-TEST", "Test User", "test@email.com");
        usuario = usuarioRepository.save(usuario);
        Optional<Usuario> encontrado = usuarioRepository.findByNroId("USR-TEST");
        assertTrue(encontrado.isPresent());
        assertEquals("Test User", encontrado.get().getNombre());
    }
} 