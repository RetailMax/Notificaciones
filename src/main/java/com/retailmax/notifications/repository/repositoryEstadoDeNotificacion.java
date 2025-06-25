package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface repositoryEstadoDeNotificacion extends JpaRepository<EstadoDeNotificacion, Long> {
    List<EstadoDeNotificacion> findByClienteId(Long clienteId);
}