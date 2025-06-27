package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PreferenciaDeNotificacionRepository extends JpaRepository<PreferenciaDeNotificacion, Long> {
    List<PreferenciaDeNotificacion> findByClienteId(Long clienteId);
    Optional<PreferenciaDeNotificacion> findByClienteIdAndCanalId(Long clienteId, Long canalId);
    void deleteByClienteId(Long clienteId);
}