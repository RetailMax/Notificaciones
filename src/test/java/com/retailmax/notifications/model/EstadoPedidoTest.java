package com.retailmax.notifications.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoPedidoTest {

    @Test
    void testValoresEnumYGetValor() {
        assertEquals("enviado", EstadoPedido.ENVIADO.getValor());
        assertEquals("en camino", EstadoPedido.EN_CAMINO.getValor());
        assertEquals("entregado", EstadoPedido.ENTREGADO.getValor());
        assertEquals("cancelado", EstadoPedido.CANCELADO.getValor());
    }

    @Test
    void testToString() {
        assertEquals("enviado", EstadoPedido.ENVIADO.toString());
        assertEquals("en camino", EstadoPedido.EN_CAMINO.toString());
        assertEquals("entregado", EstadoPedido.ENTREGADO.toString());
        assertEquals("cancelado", EstadoPedido.CANCELADO.toString());
    }

    @Test
    void testValues() {
        EstadoPedido[] values = EstadoPedido.values();
        assertEquals(4, values.length);
        assertArrayEquals(new EstadoPedido[]{
            EstadoPedido.ENVIADO,
            EstadoPedido.EN_CAMINO,
            EstadoPedido.ENTREGADO,
            EstadoPedido.CANCELADO
        }, values);
    }

    @Test
    void testValueOf() {
        assertEquals(EstadoPedido.ENVIADO, EstadoPedido.valueOf("ENVIADO"));
        assertEquals(EstadoPedido.EN_CAMINO, EstadoPedido.valueOf("EN_CAMINO"));
        assertEquals(EstadoPedido.ENTREGADO, EstadoPedido.valueOf("ENTREGADO"));
        assertEquals(EstadoPedido.CANCELADO, EstadoPedido.valueOf("CANCELADO"));
    }
} 