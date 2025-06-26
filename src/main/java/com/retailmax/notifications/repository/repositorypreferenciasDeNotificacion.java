package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PreferenciasDeNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepositoryPreferenciasDeNotificacion extends JpaRepository<PreferenciasDeNotificacion, Long> {
    List<PreferenciasDeNotificacion> findByClienteId(Long clienteId);
}
