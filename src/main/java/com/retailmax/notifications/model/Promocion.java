package com.retailmax.notifications.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Promocion")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo = "gmail"; // Siempre será gmail

    private LocalDateTime fechaEnvio;
    private String resultadoEnvio;

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getResultadoEnvio() {
        return resultadoEnvio;
    }

    public void setResultadoEnvio(String resultadoEnvio) {
        this.resultadoEnvio = resultadoEnvio;
    }
}