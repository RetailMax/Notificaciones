package com.retailmax.notifications.service;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import com.retailmax.notifications.repository.EstadoDeNotificacionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EstadoDeNotificacionServiceTest {

    private final EstadoDeNotificacionRepository repository = mock(EstadoDeNotificacionRepository.class);
    private final EstadoDeNotificacionService service = new EstadoDeNotificacionService(repository);

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertThat(service.findAll()).isEmpty();
    }

    @Test
    void testFindById() {
        EstadoDeNotificacion estado = new EstadoDeNotificacion(1L, "enviada");
        when(repository.findById(1L)).thenReturn(Optional.of(estado));
        assertThat(service.findById(1L)).contains(estado);
    }

    @Test
    void testSave() {
        EstadoDeNotificacion estado = new EstadoDeNotificacion(null, "enviada");
        when(repository.save(estado)).thenReturn(new EstadoDeNotificacion(1L, "enviada"));
        assertThat(service.save(estado).getId()).isEqualTo(1L);
    }
}