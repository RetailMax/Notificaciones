package com.retailmax.notifications.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DireccionTest {

    @Test
    void testConstructorPorDefectoYSettersYGetters() {
        Direccion direccion = new Direccion();
        direccion.setId(1L);
        direccion.setCalle("Calle 1");
        direccion.setNumero("123");
        direccion.setPiso("2");
        direccion.setDepartamento("A");
        direccion.setCodigoPostal("1000");
        direccion.setCiudad("Ciudad Test");
        direccion.setProvincia("Provincia Test");
        direccion.setPais("Pais Test");
        direccion.setTipoDireccion("ENTREGA");

        assertEquals(1L, direccion.getId());
        assertEquals("Calle 1", direccion.getCalle());
        assertEquals("123", direccion.getNumero());
        assertEquals("2", direccion.getPiso());
        assertEquals("A", direccion.getDepartamento());
        assertEquals("1000", direccion.getCodigoPostal());
        assertEquals("Ciudad Test", direccion.getCiudad());
        assertEquals("Provincia Test", direccion.getProvincia());
        assertEquals("Pais Test", direccion.getPais());
        assertEquals("ENTREGA", direccion.getTipoDireccion());
    }

    @Test
    void testConstructorConParametros() {
        Direccion direccion = new Direccion(
            2L, "Calle 2", "456", "3", "B", "2000",
            "Ciudad Param", "Provincia Param", "Pais Param", "FACTURACION"
        );
        assertEquals(2L, direccion.getId());
        assertEquals("Calle 2", direccion.getCalle());
        assertEquals("456", direccion.getNumero());
        assertEquals("3", direccion.getPiso());
        assertEquals("B", direccion.getDepartamento());
        assertEquals("2000", direccion.getCodigoPostal());
        assertEquals("Ciudad Param", direccion.getCiudad());
        assertEquals("Provincia Param", direccion.getProvincia());
        assertEquals("Pais Param", direccion.getPais());
        assertEquals("FACTURACION", direccion.getTipoDireccion());
    }

    @Test
    void testToString() {
        Direccion direccion = new Direccion(
            3L, "Calle 3", "789", "4", "C", "3000",
            "Ciudad ToString", "Provincia ToString", "Pais ToString", "OTRO"
        );
        String str = direccion.toString();
        assertTrue(str.contains("id=3"));
        assertTrue(str.contains("Calle 3"));
        assertTrue(str.contains("789"));
        assertTrue(str.contains("Provincia ToString"));
        assertTrue(str.contains("OTRO"));
    }
} 