package com.notificaciones.notificacionesFullstack.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "NOTIFICACIONPAGO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)    
    private String fecha;

    @Column(nullable = false) //Codigo que identifica Enviado, Fallido, en Cola 
    private String estado;
}