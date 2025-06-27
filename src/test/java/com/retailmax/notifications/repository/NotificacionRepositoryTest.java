package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.Notificacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository repository;

    @Test
    void guardarYBuscarPorClienteId() {
        Notificacion n = new Notificacion(null, 10L, 20L, 1L, "estado_pedido", 2L, "Mensaje", LocalDateTime.now(), "enviada");
        repository.save(n);

        List<Notificacion> resultado = repository.findByClienteId(10L);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getClienteId()).isEqualTo(10L);
    }
}