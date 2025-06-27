package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Long notificacionId;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "canal_id")
    private Long canalId;

    @Column(name = "tipo_notificacion")
    private String tipoNotificacion;

    @Column(name = "estado_notificacion_id")
    private Long estadoNotificacionId;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "resultado_envio")
    private String resultadoEnvio;
}