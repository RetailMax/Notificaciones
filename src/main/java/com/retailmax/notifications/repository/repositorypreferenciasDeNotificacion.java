package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.preferenciasDeNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface repositorypreferenciasDeNotificacion extends JpaRepository<preferenciasDeNotificacion, Long> {
    List<preferenciasDeNotificacion> findByClienteId(Long clienteId);
}

