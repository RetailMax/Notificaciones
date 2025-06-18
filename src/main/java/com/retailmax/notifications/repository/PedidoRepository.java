package com.retailmax.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.retailmax.notifications.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}