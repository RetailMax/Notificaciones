package com.retailmax.notificaciones.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.retailmax.notificaciones.model.Promocion;
import com.retailmax.notificaciones.repository.PromocionRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @InjectMocks
    private PromocionService promocionService;

    @Test
    public void testListarTodas() {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setTipo("gmail");
        promocion.setFechaEnvio(LocalDateTime.now());
        promocion.setResultadoEnvio("Enviado");

        when(promocionRepository.findAll()).thenReturn(List.of(promocion));

        List<Promocion> promociones = promocionService.listarTodas();

        assertNotNull(promociones);
        assertEquals(1, promociones.size());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        Promocion promocion = new Promocion();
        promocion.setId(id);

        when(promocionRepository.findById(id)).thenReturn(Optional.of(promocion));

        Optional<Promocion> found = promocionService.buscarPorId(id);

        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
    }

    @Test
    public void testGuardar() {
        Promocion promocion = new Promocion();
        promocion.setTipo("gmail");

        when(promocionRepository.save(promocion)).thenReturn(promocion);

        Promocion saved = promocionService.guardar(promocion);

        assertNotNull(saved);
        assertEquals("gmail", saved.getTipo());
    }

    @Test
    public void testEliminar() {
        Long id = 1L;

        doNothing().when(promocionRepository).deleteById(id);

        promocionService.eliminar(id);

        verify(promocionRepository, times(1)).deleteById(id);
    }
}

