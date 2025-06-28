package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenciaDeNotificacionRepository extends JpaRepository<PreferenciaDeNotificacion, Long> {
    List<PreferenciaDeNotificacion> findByClienteId(Long clienteId);
}