package com.retailmax.notifications.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idPedido;
    private String usuarioId;
    private LocalDate fechaPedido; 
    private String total;
    private String estadoPedido;
    private LocalDate fechaEntrega;


}
