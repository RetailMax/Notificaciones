package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Estado_Notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDeNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_notificacion_id")
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;
}