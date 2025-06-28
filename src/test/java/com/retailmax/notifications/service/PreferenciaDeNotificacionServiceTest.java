package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.repository.PreferenciaDeNotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PreferenciaDeNotificacionServiceTest {

    private PreferenciaDeNotificacionRepository repository;
    private PreferenciaDeNotificacionService service;

    @BeforeEach
    void setUp() {
        repository = mock(PreferenciaDeNotificacionRepository.class);
        service = new PreferenciaDeNotificacionService(repository);
    }

    @Test
    void testObtenerPorCliente() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 2L, true);
        when(repository.findByClienteId(1L)).thenReturn(Collections.singletonList(pref));

        List<PreferenciaDeNotificacion> result = service.obtenerPorCliente(1L);
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getCanalId());
    }

    @Test
    void testActualizarPreferencias() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 2L, true);
        when(repository.findByClienteId(1L)).thenReturn(Collections.singletonList(pref));

        service.actualizarPreferencias(1L, Arrays.asList(pref));
        verify(repository).deleteAll(anyList());
        verify(repository).saveAll(anyList());
    }
}