package com.retailmax.notifications.service;

import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNroId("USR-001");
        usuario.setNombre("Juan Pérez");
        usuario.setCorreoElectronico("juan@email.com");
        usuario.setEstado("ACTIVO");
    }

    @Test
    @DisplayName("Debería retornar todos los usuarios")
    void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));
        List<Usuario> result = usuarioService.findAll();
        assertEquals(1, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería encontrar usuario por ID")
    void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario result = usuarioService.findById(1L);
        assertEquals("Juan Pérez", result.getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException si no existe el usuario por ID")
    void testFindByIdNotFound() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> usuarioService.findById(2L));
    }

    @Test
    @DisplayName("Debería guardar un nuevo usuario")
    void testSaveNuevoUsuario() {
        when(usuarioRepository.existsByNroId(usuario.getNroId())).thenReturn(false);
        when(usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())).thenReturn(false);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario result = usuarioService.save(usuario);
        assertEquals("Juan Pérez", result.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Debería lanzar DuplicateKeyException si nroId ya existe al guardar")
    void testSaveUsuarioDuplicadoNroId() {
        Usuario nuevo = new Usuario();
        nuevo.setId(null);
        nuevo.setNroId("USR-001");
        nuevo.setCorreoElectronico("nuevo@email.com");
        when(usuarioRepository.existsByNroId(nuevo.getNroId())).thenReturn(true);
        assertThrows(DuplicateKeyException.class, () -> usuarioService.save(nuevo));
    }

    @Test
    @DisplayName("Debería lanzar DuplicateKeyException si email ya existe al guardar")
    void testSaveUsuarioDuplicadoEmail() {
        Usuario nuevo = new Usuario();
        nuevo.setId(null);
        nuevo.setNroId("USR-002");
        nuevo.setCorreoElectronico("duplicado@email.com");
        when(usuarioRepository.existsByNroId(nuevo.getNroId())).thenReturn(false);
        when(usuarioRepository.existsByCorreoElectronico(nuevo.getCorreoElectronico())).thenReturn(true);
        assertThrows(DuplicateKeyException.class, () -> usuarioService.save(nuevo));
    }

    @Test
    @DisplayName("Debería actualizar un usuario existente")
    void testUpdateUsuario() {
        Usuario actualizado = new Usuario();
        actualizado.setNombre("Modificado");
        actualizado.setCorreoElectronico("nuevo@email.com");
        actualizado.setEstado("INACTIVO");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByCorreoElectronico(actualizado.getCorreoElectronico())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(actualizado);
        Usuario result = usuarioService.update(1L, actualizado);
        assertEquals("Modificado", result.getNombre());
        assertEquals("INACTIVO", result.getEstado());
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException al actualizar usuario inexistente")
    void testUpdateUsuarioNotFound() {
        Usuario actualizado = new Usuario();
        actualizado.setCorreoElectronico("nuevo@email.com");
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> usuarioService.update(2L, actualizado));
    }

    @Test
    @DisplayName("Debería lanzar DuplicateKeyException si email ya existe al actualizar")
    void testUpdateUsuarioEmailDuplicado() {
        Usuario actualizado = new Usuario();
        actualizado.setCorreoElectronico("nuevo@email.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByCorreoElectronico(actualizado.getCorreoElectronico())).thenReturn(true);
        assertThrows(DuplicateKeyException.class, () -> usuarioService.update(1L, actualizado));
    }

    @Test
    @DisplayName("Debería eliminar un usuario existente")
    void testDeleteById() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);
        assertDoesNotThrow(() -> usuarioService.deleteById(1L));
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException al eliminar usuario inexistente")
    void testDeleteByIdNotFound() {
        when(usuarioRepository.existsById(2L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> usuarioService.deleteById(2L));
    }

    @Test
    @DisplayName("Debería cambiar el estado de un usuario")
    void testCambiarEstado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario result = usuarioService.cambiarEstado(1L, "INACTIVO");
        assertEquals("INACTIVO", result.getEstado());
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException al cambiar estado de usuario inexistente")
    void testCambiarEstadoNotFound() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> usuarioService.cambiarEstado(2L, "INACTIVO"));
    }
}
