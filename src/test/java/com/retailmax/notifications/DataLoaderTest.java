package com.retailmax.notifications;

import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.model.Promocion;
import com.retailmax.notifications.repository.PedidoRepository;
import com.retailmax.notifications.repository.UsuarioRepository;
import com.retailmax.notifications.repository.PromocionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DataLoaderTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PromocionRepository promocionRepository;
    @InjectMocks
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataLoader = new DataLoader();
        dataLoader.setPedidoRepository(pedidoRepository);
        dataLoader.setUsuarioRepository(usuarioRepository);
        dataLoader.setPromocionRepository(promocionRepository);
    }

    @Test
    @DisplayName("Debería ejecutar run() y cargar datos de ejemplo")
    void testRun() throws Exception {
        // Simular que el usuario no existe
        when(usuarioRepository.findByNroId(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(promocionRepository.save(any(Promocion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pedidoRepository.count()).thenReturn(0L);
        when(pedidoRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        dataLoader.run();

        // Verificar que se intentó guardar un usuario
        verify(usuarioRepository, atLeastOnce()).save(any(Usuario.class));
        // Verificar que se intentó guardar pedidos
        verify(pedidoRepository, atLeastOnce()).save(any(Pedido.class));
        // Verificar que se intentó guardar promociones
        verify(promocionRepository, atLeastOnce()).save(any(Promocion.class));
    }

    @Test
    @DisplayName("Debería ejecutar run() cuando el usuario ya existe")
    void testRunUsuarioYaExiste() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findByNroId(anyString())).thenReturn(Optional.of(usuario));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(promocionRepository.save(any(Promocion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pedidoRepository.count()).thenReturn(0L);
        when(pedidoRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        dataLoader.run();

        // No debe guardar usuario nuevo
        verify(usuarioRepository, never()).save(any(Usuario.class));
        // Pero sí debe guardar pedidos
        verify(pedidoRepository, atLeastOnce()).save(any(Pedido.class));
        // Y promociones
        verify(promocionRepository, atLeastOnce()).save(any(Promocion.class));
    }
} 