package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.repository.PreferenciaDeNotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PreferenciaDeNotificacionService {

    private final PreferenciaDeNotificacionRepository repo;

    public PreferenciaDeNotificacionService(PreferenciaDeNotificacionRepository repo) {
        this.repo = repo;
    }

    public List<PreferenciaDeNotificacion> obtenerPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public Optional<PreferenciaDeNotificacion> obtenerPorClienteYCanal(Long clienteId, Long canalId) {
        return repo.findByClienteIdAndCanalId(clienteId, canalId);
    }

    public PreferenciaDeNotificacion guardar(PreferenciaDeNotificacion pref) {
        return repo.save(pref);
    }

    public void actualizarPreferencias(Long clienteId, List<PreferenciaDeNotificacion> nuevas) {
        repo.deleteByClienteId(clienteId);
        repo.saveAll(nuevas);
    }

    public void crearPorDefecto(Long clienteId) {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(clienteId, 1L, true); // 1 = email
        repo.save(pref);
    }
}