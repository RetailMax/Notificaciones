package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PedidoNotificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoNotificacion {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private String nombre;
    
    @Column
    private String direccion;
    
    @Column
    private String nroOrden;

     @Column
    private String fechaPedido;




}
