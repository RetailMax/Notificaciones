package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByClienteId(Long clienteId);
}