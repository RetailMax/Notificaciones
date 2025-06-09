package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "notificacion_confirmacion_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionConfirmacionPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nroOrdenId;
    private String correoElectronico;
    

    
}
