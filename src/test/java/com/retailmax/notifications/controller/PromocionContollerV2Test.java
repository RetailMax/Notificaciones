package com.retailmax.notifications.controller;

import com.retailmax.notifications.assemblers.PromocionModelAssembler;
import com.retailmax.notifications.model.Promocion;
import com.retailmax.notifications.service.PromocionService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromocionControllerV2.class)
class PromocionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromocionService promocionService;

    @MockBean
    private PromocionModelAssembler assembler;

    @Test
    void testGetAllPromociones() throws Exception {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setTipo("gmail");
        promocion.setFechaEnvio(LocalDateTime.now());
        promocion.setResultadoEnvio("Enviado");

        when(promocionService.listarTodas()).thenReturn(List.of(promocion));
        when(assembler.toModel(any(Promocion.class))).thenReturn(EntityModel.of(promocion));

        mockMvc.perform(get("/api/v2/promociones").accept("application/hal+json"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPromocionByIdFound() throws Exception {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setTipo("gmail");
        promocion.setFechaEnvio(LocalDateTime.now());
        promocion.setResultadoEnvio("Enviado");

        when(promocionService.buscarPorId(1L)).thenReturn(Optional.of(promocion));
        when(assembler.toModel(any(Promocion.class))).thenReturn(EntityModel.of(promocion));

        mockMvc.perform(get("/api/v2/promociones/1").accept("application/hal+json"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPromocionByIdNotFound() throws Exception {
        when(promocionService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v2/promociones/99").accept("application/hal+json"))
                .andExpect(status().isNotFound()); // <--- Corregido aquí
    }

    @Test
    void testCreatePromocion() throws Exception {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setTipo("gmail");
        promocion.setFechaEnvio(LocalDateTime.now());
        promocion.setResultadoEnvio("Enviado");

        when(promocionService.guardar(any(Promocion.class))).thenReturn(promocion);
        when(assembler.toModel(any(Promocion.class))).thenReturn(EntityModel.of(promocion));

        String json = """
            {
                "tipo": "gmail",
                "fechaEnvio": "2024-06-25T12:00:00",
                "resultadoEnvio": "Enviado"
            }
            """;

        mockMvc.perform(post("/api/v2/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept("application/hal+json"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePromocion() throws Exception {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setTipo("gmail");
        promocion.setFechaEnvio(LocalDateTime.now());
        promocion.setResultadoEnvio("Enviado");

        when(promocionService.guardar(any(Promocion.class))).thenReturn(promocion);
        when(assembler.toModel(any(Promocion.class))).thenReturn(EntityModel.of(promocion));

        String json = """
            {
                "tipo": "gmail",
                "fechaEnvio": "2024-06-25T12:00:00",
                "resultadoEnvio": "Enviado"
            }
            """;

        mockMvc.perform(put("/api/v2/promociones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept("application/hal+json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePromocion() throws Exception {
        doNothing().when(promocionService).eliminar(1L);

        mockMvc.perform(delete("/api/v2/promociones/1"))
                .andExpect(status().isNoContent());
    }
}