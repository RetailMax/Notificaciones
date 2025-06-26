package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "direccion")
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

    // Constructor por defecto
    public Direccion() {
    }

    // Constructor con parámetros
    public Direccion(Long id, String calle, String numero, String piso, String departamento, 
                    String codigoPostal, String ciudad, String provincia, String pais, String tipoDireccion) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
        this.tipoDireccion = tipoDireccion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", piso='" + piso + '\'' +
                ", departamento='" + departamento + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", provincia='" + provincia + '\'' +
                ", pais='" + pais + '\'' +
                ", tipoDireccion='" + tipoDireccion + '\'' +
                '}';
    }
} 