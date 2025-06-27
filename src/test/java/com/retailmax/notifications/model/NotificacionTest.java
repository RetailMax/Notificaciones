package com.retailmax.notifications.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class NotificacionTest {

    @Test
    void gettersAndSettersWork() {
        Notificacion n = new Notificacion();
        n.setNotificacionId(1L);
        n.setClienteId(2L);
        n.setPedidoId(3L);
        n.setCanalId(4L);
        n.setTipoNotificacion("estado_pedido");
        n.setEstadoNotificacionId(5L);
        n.setMensaje("Mensaje de prueba");
        LocalDateTime now = LocalDateTime.now();
        n.setFechaEnvio(now);
        n.setResultadoEnvio("enviada");

        assertThat(n.getNotificacionId()).isEqualTo(1L);
        assertThat(n.getClienteId()).isEqualTo(2L);
        assertThat(n.getPedidoId()).isEqualTo(3L);
        assertThat(n.getCanalId()).isEqualTo(4L);
        assertThat(n.getTipoNotificacion()).isEqualTo("estado_pedido");
        assertThat(n.getEstadoNotificacionId()).isEqualTo(5L);
        assertThat(n.getMensaje()).isEqualTo("Mensaje de prueba");
        assertThat(n.getFechaEnvio()).isEqualTo(now);
        assertThat(n.getResultadoEnvio()).isEqualTo("enviada");
    }
}