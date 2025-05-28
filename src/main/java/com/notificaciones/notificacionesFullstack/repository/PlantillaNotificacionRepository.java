package com.notificaciones.notificacionesFullstack.repository;

import com.notificaciones.notificacionesFullstack.model.PlantillaNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantillaNotificacionRepository extends JpaRepository<PlantillaNotificacion, Integer> {
}