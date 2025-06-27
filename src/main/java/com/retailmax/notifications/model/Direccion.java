package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "La calle es requerida")
    @Size(max = 100, message = "La calle no puede exceder 100 caracteres")
    private String calle;

    @NotBlank(message = "El número es requerido")
    @Size(max = 10, message = "El número no puede exceder 10 caracteres")
    private String numero;

    @Size(max = 10, message = "El piso no puede exceder 10 caracteres")
    private String piso;

    @Size(max = 10, message = "El departamento no puede exceder 10 caracteres")
    private String departamento;

    @Size(max = 10, message = "El código postal no puede exceder 10 caracteres")
    private String codigoPostal;

    @NotBlank(message = "La ciudad es requerida")
    @Size(max = 50, message = "La ciudad no puede exceder 50 caracteres")
    private String ciudad;

    @Size(max = 50, message = "La provincia no puede exceder 50 caracteres")
    private String provincia;

    @Size(max = 50, message = "El país no puede exceder 50 caracteres")
    private String pais;

    @Size(max = 20, message = "El tipo de dirección no puede exceder 20 caracteres")
    private String tipoDireccion; // "ENTREGA", "FACTURACION", etc.

    // Si tienes lógica personalizada, agrégala aquí
} 