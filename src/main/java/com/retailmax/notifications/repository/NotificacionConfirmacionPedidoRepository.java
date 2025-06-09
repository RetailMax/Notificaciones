package com.retailmax.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.retailmax.notifications.model.NotificacionConfirmacionPedido;

public interface NotificacionConfirmacionPedidoRepository extends JpaRepository<NotificacionConfirmacionPedido, Long> {
}