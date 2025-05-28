package com.notificaciones.notificacionesFullstack.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PREFERENCIANOTIFICACION")
@Data
public class PreferenciaNotificacion {

    @Id
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "EMAIL", nullable = false)
    private String email; // Debe ser String para VARCHAR2

    @Column(name = "SMS", nullable = false)
    private Integer sms; // Debe ser Integer para NUMBER(1)
}