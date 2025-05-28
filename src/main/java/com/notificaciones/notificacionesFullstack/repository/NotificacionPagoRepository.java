package com.notificaciones.notificacionesFullstack.repository;

import com.notificaciones.notificacionesFullstack.model.NotificacionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionPagoRepository extends JpaRepository<NotificacionPago, Integer> {
    
}