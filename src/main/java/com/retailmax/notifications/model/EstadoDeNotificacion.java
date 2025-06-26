
package com.retailmax.notifications.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
public class EstadoDeNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificacion_seq")
    @SequenceGenerator(name = "notificacion_seq", sequenceName = "notificacion_seq", allocationSize = 1)
    @Column(name = "notificacion_id")
    private Long notificacionId;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "canal_id", nullable = false)
    private Long canalId;

    @Column(name = "tipo_notificacion", nullable = false)
    private String tipoNotificacion;

    @Column(name = "estado_notificacion_id", nullable = false)
    private Long estadoNotificacionId;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "resultado_envio")
    private String resultadoEnvio;

    public EstadoDeNotificacion() {}

    public EstadoDeNotificacion(Long clienteId, Long pedidoId, Long canalId, String tipoNotificacion, Long estadoNotificacionId, String mensaje, LocalDateTime fechaEnvio, String resultadoEnvio) {
        this.clienteId = clienteId;
        this.pedidoId = pedidoId;
        this.canalId = canalId;
        this.tipoNotificacion = tipoNotificacion;
        this.estadoNotificacionId = estadoNotificacionId;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.resultadoEnvio = resultadoEnvio;
    }


    public Long getNotificacionId() { return notificacionId; }
    public void setNotificacionId(Long notificacionId) { this.notificacionId = notificacionId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public Long getCanalId() { return canalId; }
    public void setCanalId(Long canalId) { this.canalId = canalId; }

    public String getTipoNotificacion() { return tipoNotificacion; }
    public void setTipoNotificacion(String tipoNotificacion) { this.tipoNotificacion = tipoNotificacion; }

    public Long getEstadoNotificacionId() { return estadoNotificacionId; }
    public void setEstadoNotificacionId(Long estadoNotificacionId) { this.estadoNotificacionId = estadoNotificacionId; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getResultadoEnvio() { return resultadoEnvio; }
    public void setResultadoEnvio(String resultadoEnvio) { this.resultadoEnvio = resultadoEnvio; }
}