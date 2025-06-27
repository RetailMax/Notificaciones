package com.retailmax.notifications.model;

import jakarta.persistence.*;

@Entity
@Table(name = "preferencia_notificacion")
public class PreferenciaDeNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preferencia_seq")
    @SequenceGenerator(name = "preferencia_seq", sequenceName = "preferencia_notificacion_seq", allocationSize = 1)
    @Column(name = "preferencia_id")
    private Long preferenciaId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "canal_id", nullable = false)
    private Long canalId;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado = true;

    public PreferenciaDeNotificacion() {}

    public PreferenciaDeNotificacion(Long clienteId, Long canalId, Boolean habilitado) {
        this.clienteId = clienteId;
        this.canalId = canalId;
        this.habilitado = habilitado;
    }

    public Long getPreferenciaId() { return preferenciaId; }
    public void setPreferenciaId(Long preferenciaId) { this.preferenciaId = preferenciaId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getCanalId() { return canalId; }
    public void setCanalId(Long canalId) { this.canalId = canalId; }

    public Boolean getHabilitado() { return habilitado; }
    public void setHabilitado(Boolean habilitado) { this.habilitado = habilitado; }
}
