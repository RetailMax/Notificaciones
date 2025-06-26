package com.retailmax.notifications.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreferenciaDeNotificacionTest {

    @Test
    void gettersAndSettersWork() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 2L, true);
        pref.setPreferenciaId(10L);

        assertEquals(10L, pref.getPreferenciaId());
        assertEquals(1L, pref.getClienteId());
        assertEquals(2L, pref.getCanalId());
        assertTrue(pref.getHabilitado());
    }

    @Test
    void defaultConstructorWorks() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion();
        pref.setClienteId(1L);
        pref.setCanalId(2L);
        pref.setHabilitado(false);

        assertEquals(1L, pref.getClienteId());
        assertEquals(2L, pref.getCanalId());
        assertFalse(pref.getHabilitado());
    }
}
