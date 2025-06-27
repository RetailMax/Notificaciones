package com.retailmax.notifications.controller;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.service.PreferenciaDeNotificacionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(
    controllers = PreferenciaDeNotificacionController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        com.retailmax.notifications.controller.PedidoController.class
    })
)
class PreferenciaDeNotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreferenciaDeNotificacionService service;

    @Test
    void getPreferencias_devuelveHateoasJson() throws Exception {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(1L, 1L, true);
        Mockito.when(service.obtenerPorCliente(1L)).thenReturn(List.of(pref));

        mockMvc.perform(get("/api/preferencias/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.preferenciaDeNotificacionList", hasSize(1)))
            .andExpect(jsonPath("$._embedded.preferenciaDeNotificacionList[0].clienteId", is(1)))
            .andExpect(jsonPath("$._embedded.preferenciaDeNotificacionList[0]._links.self.href", containsString("/api/preferencias/1")))
            .andExpect(jsonPath("$._links.self.href", containsString("/api/preferencias/1")));
    }
// testing
    @Test
    void updatePreferencias_llamaAlServicio() throws Exception {
        String body = "[{\"clienteId\":1,\"canalId\":1,\"habilitado\":true}]";
        mockMvc.perform(put("/api/preferencias/1")
                .contentType("application/json")
                .content(body))
            .andExpect(status().isOk());

        Mockito.verify(service).actualizarPreferencias(Mockito.eq(1L), Mockito.anyList());
    }

    @Test
    void updatePreferencias_agregaPorDefectoSiNingunaHabilitada() throws Exception {
        String body = "[{\"clienteId\":1,\"canalId\":2,\"habilitado\":false}]";
        mockMvc.perform(put("/api/preferencias/1")
                .contentType("application/json")
                .content(body))
            .andExpect(status().isOk());

        Mockito.verify(service).actualizarPreferencias(Mockito.eq(1L), Mockito.anyList());
    }
}