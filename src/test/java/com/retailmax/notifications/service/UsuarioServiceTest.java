package com.retailmax.notifications.service;

import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuario1 = new Usuario("USR-001", "Juan Pérez", "juan@email.com");
        usuario1.setId(1L);
        usuario1.setEstado("ACTIVO");

        usuario2 = new Usuario("USR-002", "Ana Gómez", "ana@email.com");
        usuario2.setId(2L);
        usuario2.setEstado("ACTIVO");
    }

    @Test
    @DisplayName("Debería encontrar todos los usuarios")
    void testFindAll() {
        // Given
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // When
        List<Usuario> result = usuarioService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals(usuario1, result.get(0));
        assertEquals(usuario2, result.get(1));
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería encontrar un usuario por ID")
    void testFindById() {
        // Given
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));

        // When
        Usuario result = usuarioService.findById(1L);

        // Then
        assertEquals(usuario1, result);
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando no encuentra usuario por ID")
    void testFindByIdNotFound() {
        // Given
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> usuarioService.findById(999L));
        verify(usuarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debería encontrar un usuario por número de identificación")
    void testFindByNroId() {
        // Given
        when(usuarioRepository.findByNroId("USR-001")).thenReturn(Optional.of(usuario1));

        // When
        Usuario result = usuarioService.findByNroId("USR-001");

        // Then
        assertEquals(usuario1, result);
        verify(usuarioRepository, times(1)).findByNroId("USR-001");
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando no encuentra usuario por Nro ID")
    void testFindByNroIdNotFound() {
        // Given
        when(usuarioRepository.findByNroId("USR-999")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> usuarioService.findByNroId("USR-999"));
        verify(usuarioRepository, times(1)).findByNroId("USR-999");
    }

    @Test
    @DisplayName("Debería guardar un nuevo usuario exitosamente")
    void testSaveNewUsuario() {
        // Given
        Usuario nuevoUsuario = new Usuario("USR-003", "Carlos Ruiz", "carlos@email.com");
        when(usuarioRepository.existsByNroId("USR-003")).thenReturn(false);
        when(usuarioRepository.existsByCorreoElectronico("carlos@email.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        // When
        Usuario result = usuarioService.save(nuevoUsuario);

        // Then
        assertEquals(nuevoUsuario, result);
        verify(usuarioRepository, times(1)).existsByNroId("USR-003");
        verify(usuarioRepository, times(1)).existsByCorreoElectronico("carlos@email.com");
        verify(usuarioRepository, times(1)).save(nuevoUsuario);
    }

    @Test
    @DisplayName("Debería lanzar DuplicateKeyException cuando el Nro ID ya existe")
    void testSaveDuplicateNroId() {
        // Given
        Usuario nuevoUsuario = new Usuario("USR-001", "Carlos Ruiz", "carlos@email.com");
        when(usuarioRepository.existsByNroId("USR-001")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateKeyException.class, () -> usuarioService.save(nuevoUsuario));
        verify(usuarioRepository, times(1)).existsByNroId("USR-001");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería lanzar DuplicateKeyException cuando el email ya existe")
    void testSaveDuplicateEmail() {
        // Given
        Usuario nuevoUsuario = new Usuario("USR-003", "Carlos Ruiz", "juan@email.com");
        when(usuarioRepository.existsByNroId("USR-003")).thenReturn(false);
        when(usuarioRepository.existsByCorreoElectronico("juan@email.com")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateKeyException.class, () -> usuarioService.save(nuevoUsuario));
        verify(usuarioRepository, times(1)).existsByNroId("USR-003");
        verify(usuarioRepository, times(1)).existsByCorreoElectronico("juan@email.com");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería actualizar un usuario existente exitosamente")
    void testUpdateUsuario() {
        // Given
        Usuario usuarioActualizado = new Usuario("USR-001", "Juan Pérez Actualizado", "juan.nuevo@email.com");
        usuarioActualizado.setEstado("INACTIVO");
        
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.existsByCorreoElectronico("juan.nuevo@email.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // When
        Usuario result = usuarioService.update(1L, usuarioActualizado);

        // Then
        assertEquals(usuario1, result);
        assertEquals("Juan Pérez Actualizado", usuario1.getNombre());
        assertEquals("juan.nuevo@email.com", usuario1.getCorreoElectronico());
        assertEquals("INACTIVO", usuario1.getEstado());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).existsByCorreoElectronico("juan.nuevo@email.com");
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    @DisplayName("Debería lanzar DuplicateKeyException cuando el email ya existe en actualización")
    void testUpdateDuplicateEmail() {
        // Given
        Usuario usuarioActualizado = new Usuario("USR-001", "Juan Pérez", "ana@email.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.existsByCorreoElectronico("ana@email.com")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateKeyException.class, () -> usuarioService.update(1L, usuarioActualizado));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).existsByCorreoElectronico("ana@email.com");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería eliminar un usuario por ID exitosamente")
    void testDeleteById() {
        // Given
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

        // When
        usuarioService.deleteById(1L);

        // Then
        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando no existe el usuario a eliminar")
    void testDeleteByIdNotFound() {
        // Given
        when(usuarioRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> usuarioService.deleteById(999L));
        verify(usuarioRepository, times(1)).existsById(999L);
        verify(usuarioRepository, never()).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("Debería cambiar el estado de un usuario exitosamente")
    void testCambiarEstado() {
        // Given
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        // When
        Usuario result = usuarioService.cambiarEstado(1L, "INACTIVO");

        // Then
        assertEquals(usuario1, result);
        assertEquals("INACTIVO", usuario1.getEstado());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    @DisplayName("Debería actualizar usuario sin cambiar el email (no verifica duplicado)")
    void testUpdateUsuarioSinCambioEmail() {
        Usuario usuarioActualizado = new Usuario("USR-001", "Juan Pérez Actualizado", "juan@email.com");
        usuarioActualizado.setEstado("INACTIVO");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario1);

        Usuario result = usuarioService.update(1L, usuarioActualizado);
        assertEquals(usuario1, result);
        assertEquals("Juan Pérez Actualizado", usuario1.getNombre());
        assertEquals("juan@email.com", usuario1.getCorreoElectronico());
        assertEquals("INACTIVO", usuario1.getEstado());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, never()).existsByCorreoElectronico(anyString());
        verify(usuarioRepository, times(1)).save(usuario1);
    }

    @Test
    @DisplayName("Debería guardar usuario con ID (actualización directa, sin chequeo de duplicados)")
    void testSaveUsuarioConId() {
        Usuario usuarioConId = new Usuario("USR-004", "Pedro Test", "pedro@email.com");
        usuarioConId.setId(10L);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioConId);
        Usuario result = usuarioService.save(usuarioConId);
        assertEquals(usuarioConId, result);
        verify(usuarioRepository, never()).existsByNroId(anyString());
        verify(usuarioRepository, never()).existsByCorreoElectronico(anyString());
        verify(usuarioRepository, times(1)).save(usuarioConId);
    }

    @Test
    @DisplayName("Debería lanzar NullPointerException al guardar usuario nulo")
    void testSaveUsuarioNulo() {
        assertThrows(NullPointerException.class, () -> usuarioService.save(null));
    }

    @Test
    @DisplayName("Debería lanzar NullPointerException al actualizar usuario nulo")
    void testUpdateUsuarioNulo() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario1));
        assertThrows(NullPointerException.class, () -> usuarioService.update(1L, null));
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException al eliminar usuario con ID nulo")
    void testDeleteByIdNulo() {
        assertThrows(EntityNotFoundException.class, () -> usuarioService.deleteById(null));
    }
}