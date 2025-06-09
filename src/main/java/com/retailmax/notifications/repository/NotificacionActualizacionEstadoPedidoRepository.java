package com.retailmax.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.retailmax.notifications.model.NotificacionActualizacionEstadoPedido;

public interface NotificacionActualizacionEstadoPedidoRepository extends JpaRepository<NotificacionActualizacionEstadoPedido, Long> {
}