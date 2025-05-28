package com.notificaciones.notificacionesFullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PLANTILLANOTIFICACION")
@Data
public class PlantillaNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // para generar automaticamente el id.
    private Integer id;

    @Column(nullable = false)  // para que el nombre sea obligatorio, si nulltable es true es para nombre opcional
    private String nombre;

    @Column(nullable = false)
    private String contenido; // Ejemplo: "Hola {nombre}, tu pago fue realizado"
}