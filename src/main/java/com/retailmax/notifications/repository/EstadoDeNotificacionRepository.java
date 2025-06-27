package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoDeNotificacionRepository extends JpaRepository<EstadoDeNotificacion, Long> {
    EstadoDeNotificacion findByDescripcion(String descripcion);
}