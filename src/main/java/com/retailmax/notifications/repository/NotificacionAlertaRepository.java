package com.retailmax.notifications.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailmax.notifications.model.NotificacionAlerta;

@Repository
public interface NotificacionAlertaRepository extends JpaRepository<NotificacionAlerta, Long>{

List<NotificacionAlerta> findByNombre(String nombre); // Busca las Notificaciones por nombre

List<NotificacionAlerta> findByEstado(String estado); //Busca las Notificaciones por estado

List<NotificacionAlerta> findByTipo(String tipo); // Busca toda las Notificaciones por tipo

List<NotificacionAlerta> findByFechaCreacion(String fechaCreacion); // Busca las Notificaciones por fecha de creacion

List<NotificacionAlerta> findByFechaModificacion(String fechaModificacion); // Busca las Notificaciones por fecha de modificacion
}
