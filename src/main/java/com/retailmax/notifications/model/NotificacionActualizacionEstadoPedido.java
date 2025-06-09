package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificacion_actualizacion_estado_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionActualizacionEstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nroOrdenId;
    private String estado; // Ej: ENVIADO, EN CAMINO, ENTREGADO
    private String correoElectronico;


   
}