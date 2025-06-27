package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
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
    @NotNull(message = "La fecha del pedido es requerida")
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();

    /** Estado del pedido */
    @NotNull(message = "El estado del pedido es requerido")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false)
    private EstadoPedido estadoPedido = EstadoPedido.ENVIADO;

    /** Monto total del pedido */
    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Column(name = "monto_total", nullable = false)
    private BigDecimal montoTotal;

    /** Fecha estimada de entrega */
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    /** Usuario asociado al pedido */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructor parcial para tests o lógica específica
    public Pedido(String codigoId, BigDecimal montoTotal, LocalDateTime fechaEntrega) {
        this.codigoId = codigoId;
        this.montoTotal = montoTotal;
        this.fechaEntrega = fechaEntrega;
        this.fechaPedido = LocalDateTime.now();
        this.estadoPedido = EstadoPedido.ENVIADO;
    }

    // Enum para el estado del pedido
    public enum EstadoPedido {
        ENVIADO, ENTREGADO, CANCELADO
    }
}