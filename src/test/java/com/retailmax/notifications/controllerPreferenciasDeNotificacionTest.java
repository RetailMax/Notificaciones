package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.preferenciasDeNotificacion;
import com.retailmax.notifications.service.servicePreferenciasDeNotificacion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllerPreferenciasDeNotificacion.class)
class controllerPreferenciasDeNotificacionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private servicePreferenciasDeNotificacion service;

    @Test
    void testGetPreferencias() throws Exception {
        Mockito.when(service.obtenerPorCliente(1L))
                .thenReturn(Collections.singletonList(new preferenciasDeNotificacion(1L, 1L, true)));

        mockMvc.perform(get("/api/preferencias/1"))
                .andExpect(status().isOk());
    }
}