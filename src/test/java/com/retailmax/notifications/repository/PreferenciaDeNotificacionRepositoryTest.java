package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PreferenciaDeNotificacionRepositoryTest {

    @Autowired
    private PreferenciaDeNotificacionRepository repo;

    @Test
    void guardarYBuscarPorClienteId() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(99L, 1L, true);
        repo.save(pref);

        List<PreferenciaDeNotificacion> resultado = repo.findByClienteId(99L);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getClienteId()).isEqualTo(99L);
    }
}
