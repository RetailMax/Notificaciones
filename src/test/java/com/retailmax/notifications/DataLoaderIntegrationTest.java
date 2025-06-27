package com.retailmax.notifications;

import com.retailmax.notifications.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class DataLoaderIntegrationTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void testDataLoaderRun() {
        // Verifica que se hayan cargado pedidos de ejemplo
        long count = pedidoRepository.count();
        assertTrue(count >= 15, "Se esperaban al menos 15 pedidos de ejemplo");
    }
} 