package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long pedidoId;

    @NotNull(message = "El ID del cliente es requerido")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @NotNull(message = "La fecha del pedido es requerida")
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @NotNull(message = "El estado del pedido es requerido")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false)
    private EstadoPedido estadoPedido = EstadoPedido.ENVIADO;

    @NotNull(message = "El total es requerido")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructor parcial requerido por los tests
    public Pedido(Long clienteId, BigDecimal total, LocalDateTime fechaEntregaEstimada) {
        this.clienteId = clienteId;
        this.total = total;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.fechaPedido = LocalDateTime.now();
        this.estadoPedido = EstadoPedido.ENVIADO;
    }

    // Si tienes lógica personalizada, agrégala aquí
}