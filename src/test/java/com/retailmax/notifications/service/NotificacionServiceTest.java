package com.retailmax.notifications.service;

import com.retailmax.notifications.model.Notificacion;
import com.retailmax.notifications.repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    private final NotificacionRepository repo = mock(NotificacionRepository.class);
    private final NotificacionService service = new NotificacionService(repo);

    @Test
    void testRegistrar() {
        Notificacion n = new Notificacion(null, 1L, 2L, 3L, "estado_pedido", 4L, "Mensaje", LocalDateTime.now(), "enviada");
        when(repo.save(n)).thenReturn(n);
        Notificacion result = service.registrar(n);
        assertThat(result).isEqualTo(n);
    }

    @Test
    void testObtenerTodas() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        assertThat(service.obtenerTodas()).isEmpty();
    }

    @Test
    void testBuscarPorId() {
        Notificacion n = new Notificacion(1L, 1L, 2L, 3L, "estado_pedido", 4L, "Mensaje", LocalDateTime.now(), "enviada");
        when(repo.findById(1L)).thenReturn(Optional.of(n));
        assertThat(service.buscarPorId(1L)).contains(n);
    }

    @Test
    void testObtenerPorCliente() {
        when(repo.findByClienteId(1L)).thenReturn(Collections.emptyList());
        assertThat(service.obtenerPorCliente(1L)).isEmpty();
    }
}