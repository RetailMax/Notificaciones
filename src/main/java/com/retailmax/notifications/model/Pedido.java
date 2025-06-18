package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name ="pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoId;
    private Date fechaPedido; 
    private String estadoPedido;
    private String montoTotal;
    private Date fechaEntrega;

    @ManyToOne
    @JoinColumn(name = "usuarionroid", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "direccion", nullable = false)
    private Direccion direccionEntrega;

}