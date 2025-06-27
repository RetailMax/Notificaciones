package com.retailmax.notifications.assemblers;

import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.model.EstadoPedido;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PedidoModelAssemblerTest {

    @Test
    void toModel_deberiaRetornarEntityModelConLinks() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setPedidoId(1L);
        pedido.setClienteId(100L);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido(EstadoPedido.ENVIADO);
        pedido.setTotal(new BigDecimal("100.00"));

        PedidoModelAssembler assembler = new PedidoModelAssembler();

        // Act
        EntityModel<Pedido> model = assembler.toModel(pedido);

        // Assert
        assertNotNull(model);
        assertEquals(pedido, model.getContent());
        assertTrue(model.getLinks().hasLink("self"));
        assertTrue(model.getLinks().hasLink("pedidos"));
    }
} 