package com.retailmax.notifications.assemblers;

import com.retailmax.notifications.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PedidoModelAssemblersTest {

    @Test
    void toModel_deberiaRetornarEntityModelConLinks() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCodigoId("PED-001");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido(Pedido.EstadoPedido.ENVIADO);
        pedido.setMontoTotal(new BigDecimal("100.00"));

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