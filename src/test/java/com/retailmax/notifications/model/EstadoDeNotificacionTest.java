package com.retailmax.notifications.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class EstadoDeNotificacionTest {

    @Test
    void testLombokGettersSetters() {
        EstadoDeNotificacion estado = new EstadoDeNotificacion();
        estado.setId(1L);
        estado.setDescripcion("enviada");
        assertThat(estado.getId()).isEqualTo(1L);
        assertThat(estado.getDescripcion()).isEqualTo("enviada");
    }
}