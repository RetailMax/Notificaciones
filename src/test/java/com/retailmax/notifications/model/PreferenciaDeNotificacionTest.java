package com.retailmax.notifications.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PreferenciaDeNotificacionTest {

    @Test
    void testConstructorAndGetters() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(10L, 2L, true);
        pref.setPreferenciaId(5L);

        assertEquals(5L, pref.getPreferenciaId());
        assertEquals(10L, pref.getClienteId());
        assertEquals(2L, pref.getCanalId());
        assertTrue(pref.getHabilitado());
    }

    @Test
    void testSetters() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion();
        pref.setPreferenciaId(1L);
        pref.setClienteId(2L);
        pref.setCanalId(3L);
        pref.setHabilitado(false);

        assertEquals(1L, pref.getPreferenciaId());
        assertEquals(2L, pref.getClienteId());
        assertEquals(3L, pref.getCanalId());
        assertFalse(pref.getHabilitado());
    }
}