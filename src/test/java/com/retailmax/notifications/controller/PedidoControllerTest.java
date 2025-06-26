package com.retailmax.notifications.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.retailmax.notifications.model.EstadoPedido;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.service.PedidoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PedidoController.class)
@DisplayName("Pruebas del Controlador de Pedidos")
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido;
    private List<Pedido> pedidos;

    @BeforeEach
    void setUp() {
        // Configurar ObjectMapper para manejar LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        
        // Crear pedido de prueba
        pedido = new Pedido();
        pedido.setPedidoId(1L);
        pedido.setClienteId(1001L);
        pedido.setFechaPedido(LocalDateTime.of(2024, 1, 15, 10, 30));
        pedido.setEstadoPedido(EstadoPedido.ENVIADO);
        pedido.setTotal(new BigDecimal("15000.50"));
        pedido.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 20, 14, 0));

        // Crear lista de pedidos
        Pedido pedido2 = new Pedido();
        pedido2.setPedidoId(2L);
        pedido2.setClienteId(1002L);
        pedido2.setFechaPedido(LocalDateTime.of(2024, 1, 16, 11, 0));
        pedido2.setEstadoPedido(EstadoPedido.EN_CAMINO);
        pedido2.setTotal(new BigDecimal("25000.75"));
        pedido2.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 22, 16, 0));

        pedidos = Arrays.asList(pedido, pedido2);
    }

    @Test
    @DisplayName("Debería listar todos los pedidos exitosamente")
    void testListarPedidos() throws Exception {
        // Arrange
        when(pedidoService.findAll()).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/v2/pedido"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].pedidoId").value(1))
                .andExpect(jsonPath("$[0].clienteId").value(1001))
                .andExpect(jsonPath("$[0].estadoPedido").value("ENVIADO"))
                .andExpect(jsonPath("$[0].total").value(15000.50))
                .andExpect(jsonPath("$[1].pedidoId").value(2))
                .andExpect(jsonPath("$[1].clienteId").value(1002))
                .andExpect(jsonPath("$[1].estadoPedido").value("EN_CAMINO"));

        verify(pedidoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar 204 cuando no hay pedidos")
    void testListarPedidosVacio() throws Exception {
        // Arrange
        when(pedidoService.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/v2/pedido"))
                .andExpect(status().isNoContent());

        verify(pedidoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un pedido por ID exitosamente")
    void testObtenerPedidoPorId() throws Exception {
        // Arrange
        when(pedidoService.findById(1L)).thenReturn(Optional.of(pedido));

        // Act & Assert
        mockMvc.perform(get("/api/v2/pedido/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.clienteId").value(1001))
                .andExpect(jsonPath("$.estadoPedido").value("ENVIADO"))
                .andExpect(jsonPath("$.total").value(15000.50));

        verify(pedidoService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar 404 cuando el pedido no existe")
    void testObtenerPedidoPorIdNoExiste() throws Exception {
        // Arrange
        when(pedidoService.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v2/pedido/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debería crear un nuevo pedido exitosamente")
    void testCrearPedido() throws Exception {
        // Arrange
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setClienteId(1003L);
        nuevoPedido.setTotal(new BigDecimal("5000.25"));
        nuevoPedido.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 25, 12, 0));

        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setPedidoId(3L);
        pedidoGuardado.setClienteId(1003L);
        pedidoGuardado.setFechaPedido(LocalDateTime.now());
        pedidoGuardado.setEstadoPedido(EstadoPedido.ENVIADO);
        pedidoGuardado.setTotal(new BigDecimal("5000.25"));
        pedidoGuardado.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 25, 12, 0));

        when(pedidoService.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        // Act & Assert
        mockMvc.perform(post("/api/v2/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoPedido)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pedidoId").value(3))
                .andExpect(jsonPath("$.clienteId").value(1003))
                .andExpect(jsonPath("$.estadoPedido").value("ENVIADO"))
                .andExpect(jsonPath("$.total").value(5000.25));

        verify(pedidoService, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería actualizar un pedido existente exitosamente")
    void testActualizarPedido() throws Exception {
        // Arrange
        Pedido pedidoActualizado = new Pedido();
        pedidoActualizado.setClienteId(1001L);
        pedidoActualizado.setEstadoPedido(EstadoPedido.EN_CAMINO);
        pedidoActualizado.setTotal(new BigDecimal("16000.75"));
        pedidoActualizado.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 21, 15, 0));

        Pedido pedidoRespuesta = new Pedido();
        pedidoRespuesta.setPedidoId(1L);
        pedidoRespuesta.setClienteId(1001L);
        pedidoRespuesta.setFechaPedido(LocalDateTime.of(2024, 1, 15, 10, 30));
        pedidoRespuesta.setEstadoPedido(EstadoPedido.EN_CAMINO);
        pedidoRespuesta.setTotal(new BigDecimal("16000.75"));
        pedidoRespuesta.setFechaEntregaEstimada(LocalDateTime.of(2024, 1, 21, 15, 0));

        when(pedidoService.existsById(1L)).thenReturn(true);
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedidoRespuesta);

        // Act & Assert
        mockMvc.perform(put("/api/v2/pedido/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoActualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pedidoId").value(1))
                .andExpect(jsonPath("$.estadoPedido").value("EN_CAMINO"))
                .andExpect(jsonPath("$.total").value(16000.75));

        verify(pedidoService, times(1)).existsById(1L);
        verify(pedidoService, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería retornar 404 al actualizar un pedido que no existe")
    void testActualizarPedidoNoExiste() throws Exception {
        // Arrange
        Pedido pedidoActualizado = new Pedido();
        pedidoActualizado.setClienteId(1001L);
        pedidoActualizado.setTotal(new BigDecimal("16000.75"));

        when(pedidoService.existsById(999L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(put("/api/v2/pedido/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoActualizado)))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).existsById(999L);
        verify(pedidoService, never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería eliminar un pedido existente exitosamente")
    void testEliminarPedido() throws Exception {
        // Arrange
        when(pedidoService.existsById(1L)).thenReturn(true);
        doNothing().when(pedidoService).deleteById(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/v2/pedido/1"))
                .andExpect(status().isNoContent());

        verify(pedidoService, times(1)).existsById(1L);
        verify(pedidoService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería retornar 404 al eliminar un pedido que no existe")
    void testEliminarPedidoNoExiste() throws Exception {
        // Arrange
        when(pedidoService.existsById(999L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/v2/pedido/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).existsById(999L);
        verify(pedidoService, never()).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("Debería validar datos requeridos al crear pedido")
    void testCrearPedidoDatosInvalidos() throws Exception {
        // Arrange - Pedido sin datos requeridos
        Pedido pedidoInvalido = new Pedido();
        pedidoInvalido.setClienteId(null); // ClienteId es requerido
        pedidoInvalido.setTotal(null); // Total es requerido
        pedidoInvalido.setFechaPedido(null); // FechaPedido es requerido
        pedidoInvalido.setEstadoPedido(null); // EstadoPedido es requerido

        // Act & Assert
        mockMvc.perform(post("/api/v2/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería validar datos requeridos al actualizar pedido")
    void testActualizarPedidoDatosInvalidos() throws Exception {
        // Arrange
        Pedido pedidoInvalido = new Pedido();
        pedidoInvalido.setClienteId(null); // ClienteId es requerido
        pedidoInvalido.setTotal(null); // Total es requerido
        pedidoInvalido.setFechaPedido(null); // FechaPedido es requerido
        pedidoInvalido.setEstadoPedido(null); // EstadoPedido es requerido

        // No se debe invocar existsById si la validación falla

        // Act & Assert
        mockMvc.perform(put("/api/v2/pedido/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).existsById(anyLong());
        verify(pedidoService, never()).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Debería manejar errores internos del servidor")
    void testErrorInternoServidor() throws Exception {
        // Arrange
        when(pedidoService.findAll()).thenThrow(new RuntimeException("Error interno"));

        // Act & Assert
        mockMvc.perform(get("/api/v2/pedido"))
                .andExpect(status().isInternalServerError());

        verify(pedidoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería validar formato de JSON inválido")
    void testJsonInvalido() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v2/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ invalid json }"))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).save(any(Pedido.class));
    }
} 