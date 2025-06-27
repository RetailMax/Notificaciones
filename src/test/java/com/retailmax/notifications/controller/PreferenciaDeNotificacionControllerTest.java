package com.retailmax.notifications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.service.PreferenciaDeNotificacionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PreferenciaDeNotificacionController.class)
class PreferenciaDeNotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreferenciaDeNotificacionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerPreferenciasPorCliente() throws Exception {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 1L, true);
        Mockito.when(service.obtenerPorCliente(1L)).thenReturn(Collections.singletonList(pref));

        mockMvc.perform(get("/api/preferencias/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.entityModelList[0].clienteId").value(1L));
    }

    @Test
    void testActualizarPreferencias() throws Exception {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 2L, false);
        String json = objectMapper.writeValueAsString(Arrays.asList(pref));

        mockMvc.perform(put("/api/preferencias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNoContent());
    }
}