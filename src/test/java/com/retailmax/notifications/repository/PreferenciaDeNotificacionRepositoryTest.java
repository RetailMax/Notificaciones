package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PreferenciaDeNotificacionRepositoryTest {

    @Autowired
    private PreferenciaDeNotificacionRepository repository;

    @Test
    void testFindByClienteId() {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(100L, 1L, true);
        repository.save(pref);

        List<PreferenciaDeNotificacion> result = repository.findByClienteId(100L);
        assertFalse(result.isEmpty());
        assertEquals(100L, result.get(0).getClienteId());
    }
}