package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EstadoDeNotificacionRepositoryTest {

    @Autowired
    private EstadoDeNotificacionRepository repository;

    @Test
    void testSaveAndFindByDescripcion() {
        EstadoDeNotificacion estado = new EstadoDeNotificacion(null, "enviada");
        repository.save(estado);

        EstadoDeNotificacion found = repository.findByDescripcion("enviada");
        assertThat(found).isNotNull();
        assertThat(found.getDescripcion()).isEqualTo("enviada");
    }
}