package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
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
    private LocalDateTime fechaPedido;

    @NotNull(message = "El estado del pedido es requerido")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pedido", nullable = false)
    private EstadoPedido estadoPedido;

    @NotNull(message = "El total es requerido")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_entrega_estimada")
    private LocalDateTime fechaEntregaEstimada;

    // Constructor por defecto
    public Pedido() {
        this.fechaPedido = LocalDateTime.now();
        this.estadoPedido = EstadoPedido.ENVIADO;
    }

    // Constructor con parámetros
    public Pedido(Long clienteId, BigDecimal total, LocalDateTime fechaEntregaEstimada) {
        this.clienteId = clienteId;
        this.total = total;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.fechaPedido = LocalDateTime.now();
        this.estadoPedido = EstadoPedido.ENVIADO;
    }

    // Getters y Setters
    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(LocalDateTime fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "pedidoId=" + pedidoId +
                ", clienteId=" + clienteId +
                ", fechaPedido=" + fechaPedido +
                ", estadoPedido=" + estadoPedido +
                ", total=" + total +
                ", fechaEntregaEstimada=" + fechaEntregaEstimada +
                '}';
    }
}