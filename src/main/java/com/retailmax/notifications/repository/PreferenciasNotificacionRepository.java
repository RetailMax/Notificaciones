package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PreferenciasNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenciasNotificacionRepository extends JpaRepository<PreferenciasNotificacion, Long> {
}