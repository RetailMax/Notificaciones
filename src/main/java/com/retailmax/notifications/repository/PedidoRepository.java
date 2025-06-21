package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

     /**
     * Busca todos los pedidos de un cliente específico
     * @param clienteId el ID del cliente
     * @return lista de pedidos del cliente
     */
    List<Pedido> findByClienteId(Long clienteId);

    /**
     * Busca pedidos por estado
     * @param estadoPedido el estado del pedido
     * @return lista de pedidos con el estado especificado
     */
    List<Pedido> findByEstadoPedido(EstadoPedido estadoPedido);

    /**
     * Busca pedidos de un cliente con un estado específico
     * @param clienteId el ID del cliente
     * @param estadoPedido el estado del pedido
     * @return lista de pedidos que coinciden
     */
    List<Pedido> findByClienteIdAndEstadoPedido(Long clienteId, EstadoPedido estadoPedido);

    /**
     * Busca pedidos realizados en un rango de fechas
     * @param fechaInicio fecha de inicio del rango
     * @param fechaFin fecha de fin del rango
     * @return lista de pedidos en el rango de fechas
     */
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByFechaPedidoBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                         @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Busca pedidos con entrega estimada vencida
     * @param fechaActual la fecha actual para comparar
     * @return lista de pedidos con entrega vencida
     */
    @Query("SELECT p FROM Pedido p WHERE p.fechaEntregaEstimada < :fechaActual AND p.estadoPedido != 'ENTREGADO' AND p.estadoPedido != 'CANCELADO'")
    List<Pedido> findPedidosVencidos(@Param("fechaActual") LocalDateTime fechaActual);

    /**
     * Cuenta el número de pedidos de un cliente
     * @param clienteId el ID del cliente
     * @return número de pedidos del cliente
     */
    long countByClienteId(Long clienteId);
}