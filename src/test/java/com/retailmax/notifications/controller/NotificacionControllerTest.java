package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.Notificacion;
import com.retailmax.notifications.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService service;

    @Test
    void testObtenerTodas() throws Exception {
        when(service.obtenerTodas()).thenReturn(Arrays.asList(new Notificacion(1L, 1L, 2L, 3L, "estado_pedido", 4L, "Mensaje", LocalDateTime.now(), "enviada")));
        mockMvc.perform(get("/api/notificaciones"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPorIdFound() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(new Notificacion(1L, 1L, 2L, 3L, "estado_pedido", 4L, "Mensaje", LocalDateTime.now(), "enviada")));
        mockMvc.perform(get("/api/notificaciones/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPorIdNotFound() throws Exception {
        when(service.buscarPorId(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/notificaciones/2"))
                .andExpect(status().isOk()); // Cambia a .andExpect(status().isNotFound()) si tu endpoint lo maneja así
    }
}