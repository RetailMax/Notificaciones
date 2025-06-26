package com.retailmax.notifications.service;

import com.retailmax.notifications.service.servicePreferenciasDeNotificacion;
import com.retailmax.notifications.repository.repositorypreferenciasDeNotificacion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class servicePreferenciasDeNotificacionTest {
    @Test
    void testObtenerPorCliente() {
        repositorypreferenciasDeNotificacion repo = Mockito.mock(repositorypreferenciasDeNotificacion.class);
        servicePreferenciasDeNotificacion service = new servicePreferenciasDeNotificacion(repo);
        Mockito.when(repo.findByClienteId(1L)).thenReturn(Collections.singletonList(new preferenciasDeNotificacion(1L, 1L, true)));
        List<preferenciasDeNotificacion> resultado = service.obtenerPorCliente(1L);
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getClienteId()).isEqualTo(1L);
    }
}
