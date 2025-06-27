package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "preferencia_notificacion")
public class PreferenciaDeNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenciaId;

    @NotNull
    private Long clienteId;

    @NotNull
    private Long canalId;

    @NotNull
    private Boolean habilitado;

    public PreferenciaDeNotificacion() {}

    public PreferenciaDeNotificacion(Long clienteId, Long canalId, Boolean habilitado) {
        this.clienteId = clienteId;
        this.canalId = canalId;
        this.habilitado = habilitado;
    }

    // Getters y setters
    public Long getPreferenciaId() { return preferenciaId; }
    public void setPreferenciaId(Long preferenciaId) { this.preferenciaId = preferenciaId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getCanalId() { return canalId; }
    public void setCanalId(Long canalId) { this.canalId = canalId; }
    public Boolean getHabilitado() { return habilitado; }
    public void setHabilitado(Boolean habilitado) { this.habilitado = habilitado; }
}