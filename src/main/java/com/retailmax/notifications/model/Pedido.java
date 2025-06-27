package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Modelo de Pedido del sistema RetailMax
 */
public class Pedido {

    /** ID único del pedido */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código único del pedido */
    @NotBlank(message = "El código es obligatorio")
    @Column(name = "codigo_id", unique = true, length = 30)
    private String codigoId;

    /** Fecha del pedido */
    @NotNull(message = "La fecha del pedido es obligatoria")
    @Column(name = "fecha_pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;

    /** Estado del pedido */
    @NotBlank(message = "El estado del pedido es obligatorio")
    @Column(name = "estado_pedido", length = 20)
    private String estadoPedido;

    /** Monto total del pedido */
    @NotBlank(message = "El monto total es obligatorio")
    @Column(name = "monto_total")
    private String montoTotal;

    /** Fecha estimada de entrega */
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;

    /** Usuario asociado al pedido */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}