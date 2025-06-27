package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import com.retailmax.notifications.service.EstadoDeNotificacionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstadoDeNotificacionController.class)
class EstadoDeNotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadoDeNotificacionService service;

    @Test
    void testGetAll() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(new EstadoDeNotificacion(1L, "enviada")));
        mockMvc.perform(get("/api/estados-notificacion"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByIdFound() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(new EstadoDeNotificacion(1L, "enviada")));
        mockMvc.perform(get("/api/estados-notificacion/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(service.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/estados-notificacion/2"))
                .andExpect(status().isNotFound());
    }
}