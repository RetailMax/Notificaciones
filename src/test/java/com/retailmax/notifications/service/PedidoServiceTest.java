package com.retailmax.notifications.service;

import com.retailmax.notifications.model.EstadoPedido;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas del Servicio de Pedidos")
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedido = new Pedido();
        pedido.setPedidoId(1L);
        pedido.setClienteId(1001L);
        pedido.setFechaPedido(LocalDateTime.of(2024, 1, 15, 10, 30));
        pedido.setEstadoPedido(EstadoPedido.ENVIADO);
        pedido.setTotal(new BigDecimal("15000.50"));
        pedido.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 20, 14, 0));
    }

    @Test
    @DisplayName("Debería retornar todos los pedidos")
    void testFindAll() {
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));
        List<Pedido> result = pedidoService.findAll();
        assertEquals(1, result.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar un pedido por ID si existe")
    void testFindByIdExiste() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        Optional<Pedido> result = pedidoService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getPedidoId());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar vacío si el pedido no existe")
    void testFindByIdNoExiste() {
        when(pedidoRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Pedido> result = pedidoService.findById(2L);
        assertFalse(result.isPresent());
        verify(pedidoRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Debería guardar un pedido correctamente")
    void testSave() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        Pedido result = pedidoService.save(pedido);
        assertNotNull(result);
        assertEquals(1L, result.getPedidoId());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    @DisplayName("Debería eliminar un pedido existente")
    void testDeleteByIdExiste() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(1L);
        pedidoService.deleteById(1L);
        verify(pedidoRepository, times(1)).existsById(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("No debería eliminar si el pedido no existe")
    void testDeleteByIdNoExiste() {
        when(pedidoRepository.existsById(2L)).thenReturn(false);
        pedidoService.deleteById(2L);
        verify(pedidoRepository, times(1)).existsById(2L);
        verify(pedidoRepository, never()).deleteById(2L);
    }

    @Test
    @DisplayName("Debería verificar existencia de pedido por ID")
    void testExistsById() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        boolean exists = pedidoService.existsById(1L);
        assertTrue(exists);
        verify(pedidoRepository, times(1)).existsById(1L);
    }
}
