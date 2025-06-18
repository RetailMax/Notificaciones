package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calle;
    private String numero;
    private String piso;
    private String departamento;
    private String codigoPostal;
    private String ciudad;
    private String provincia;
    private String pais;
    private String tipoDireccion; // "ENTREGA", "FACTURACION", etc.
} 