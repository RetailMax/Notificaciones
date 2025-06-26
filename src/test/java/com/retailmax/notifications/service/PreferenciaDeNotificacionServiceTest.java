package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.repository.PreferenciaDeNotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreferenciaDeNotificacionServiceTest {

    @Mock
    private PreferenciaDeNotificacionRepository repo;

    @InjectMocks
    private PreferenciaDeNotificacionService service;

    private Long clienteId;
    private PreferenciaDeNotificacion p1, p2;

    @BeforeEach
    void setup() {
        clienteId = 42L;
        p1 = new PreferenciaDeNotificacion(clienteId, 1L, true);
        p2 = new PreferenciaDeNotificacion(clienteId, 2L, false);
    }

    @Test
    void obtenerPorCliente_devuelveListaCorrecta() {
        when(repo.findByClienteId(clienteId)).thenReturn(List.of(p1, p2));

        List<PreferenciaDeNotificacion> resultado = service.obtenerPorCliente(clienteId);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(p1));
        verify(repo).findByClienteId(clienteId);
    }

    @Test
    void crearPorDefecto_guardaUnaPreferencia() {
        service.crearPorDefecto(clienteId);

        verify(repo).save(any(PreferenciaDeNotificacion.class));
    }

    @Test
    void actualizarPreferencias_reemplazaTodas() {
        when(repo.findByClienteId(clienteId)).thenReturn(List.of(p1, p2));
        List<PreferenciaDeNotificacion> nuevas = List.of(
            new PreferenciaDeNotificacion(clienteId, 3L, true)
        );

        service.actualizarPreferencias(clienteId, nuevas);

        verify(repo).deleteAll(List.of(p1, p2));
        verify(repo).saveAll(nuevas);
    }
}