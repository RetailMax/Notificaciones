package com.notificaciones.notificacionesFullstack.repository;

import com.notificaciones.notificacionesFullstack.model.PreferenciaNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenciaNotificacionRepository extends JpaRepository<PreferenciaNotificacion, Integer> {
}