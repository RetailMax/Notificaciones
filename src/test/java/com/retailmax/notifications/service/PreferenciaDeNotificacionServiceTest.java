package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.repository.PreferenciaDeNotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreferenciaDeNotificacionServiceTest {

    @Mock
    private PreferenciaDeNotificacionRepository repo;

    @InjectMocks
    private PreferenciaDeNotificacionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerPorCliente() {
        List<PreferenciaDeNotificacion> prefs = List.of(new PreferenciaDeNotificacion(1L, 1L, true));
        when(repo.findByClienteId(1L)).thenReturn(prefs);
        List<PreferenciaDeNotificacion> result = service.obtenerPorCliente(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorClienteYCanal() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 2L, true);
        when(repo.findByClienteIdAndCanalId(1L, 2L)).thenReturn(Optional.of(pref));
        Optional<PreferenciaDeNotificacion> result = service.obtenerPorClienteYCanal(1L, 2L);
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getCanalId());
    }

    @Test
    void testGuardar() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 1L, true);
        when(repo.save(pref)).thenReturn(pref);
        PreferenciaDeNotificacion result = service.guardar(pref);
        assertEquals(pref, result);
    }

    @Test
    void testActualizarPreferencias() {
        doNothing().when(repo).deleteByClienteId(1L);
        when(repo.saveAll(anyList())).thenReturn(Collections.emptyList());
        service.actualizarPreferencias(1L, List.of(new PreferenciaDeNotificacion(1L, 1L, true)));
        verify(repo).deleteByClienteId(1L);
        verify(repo).saveAll(anyList());
    }

    @Test
    void testCrearPorDefecto() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 1L, true);
        when(repo.save(any())).thenReturn(pref);
        service.crearPorDefecto(1L);
        verify(repo).save(any(PreferenciaDeNotificacion.class));
    }
}