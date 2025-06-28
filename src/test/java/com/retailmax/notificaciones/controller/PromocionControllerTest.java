package com.retailmax.notifications.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.retailmax.notificaciones.assemblers.PromocionModelAssembler;

import com.retailmax.notificaciones.model.Promocion;
import com.retailmax.notificaciones.service.PromocionService;

import org.springframework.hateoas.EntityModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PromocionController.class)
public class PromocionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromocionModelAssembler promocionModelAssembler;

    @MockBean
    private PromocionService promocionService;

  
    
    @Test
    void testCreatePromocion() throws Exception {
    Promocion promocion = new Promocion();
    promocion.setTipo("gmail");
    promocion.setFechaEnvio(LocalDateTime.now());
    promocion.setResultadoEnvio("Enviado");

    when(promocionService.guardar(any(Promocion.class))).thenReturn(promocion);
    when(promocionModelAssembler.toModel(any(Promocion.class)))
        .thenReturn(EntityModel.of(promocion));

    String json = """
        {
        "tipo": "gmail",
        "fechaEnvio": "2024-06-25T12:00:00",
        "resultadoEnvio": "Enviado"
        }
        """;

    mockMvc.perform(post("/api/promociones")
        .contentType("application/json")
        .content(json)
        .accept("application/hal+json"))
        .andExpect(status().isOk());

    // MOCK para el GET de todas las promociones
    when(promocionService.listarTodas()).thenReturn(List.of(promocion));
    when(promocionModelAssembler.toModel(any(Promocion.class)))
        .thenReturn(EntityModel.of(promocion));

    mockMvc.perform(get("/api/promociones")
        .accept("application/hal+json"))
        .andExpect(status().isOk());
    }

    @Test
    void testFiendById() throws Exception {
        when(promocionService.buscarPorId(eq(99L))).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/promociones/99"))
        .andExpect(status().isNotFound());
    }

    @Test
    void testFindByIdFound() throws Exception {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setTipo("gmail");
        promocion.setFechaEnvio(LocalDateTime.now());
        promocion.setResultadoEnvio("Enviado");

        when(promocionService.buscarPorId(eq(1L))).thenReturn(Optional.of(promocion));
        when(promocionModelAssembler.toModel(any(Promocion.class)))
            .thenReturn(EntityModel.of(promocion));

        mockMvc.perform(get("/api/promociones/1")
            .accept("application/hal+json"))
            .andExpect(status().isOk());
    }
}