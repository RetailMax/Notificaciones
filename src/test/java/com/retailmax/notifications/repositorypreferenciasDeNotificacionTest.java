package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.preferenciasDeNotificacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class repositorypreferenciasDeNotificacionTest {

    @Autowired
    private repositorypreferenciasDeNotificacion repo;

    @Test
    void testGuardarYBuscarPorClienteId() {
        preferenciasDeNotificacion pref = new preferenciasDeNotificacion(1L, 1L, true);
        repo.save(pref);

        List<preferenciasDeNotificacion> resultado = repo.findByClienteId(1L);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getClienteId()).isEqualTo(1L);
    }
}