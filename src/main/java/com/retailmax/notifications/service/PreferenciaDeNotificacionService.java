package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.repository.PreferenciaDeNotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PreferenciaDeNotificacionService {

    private final PreferenciaDeNotificacionRepository repo;

    public PreferenciaDeNotificacionService(PreferenciaDeNotificacionRepository repo) {
        this.repo = repo;
    }

    public List<PreferenciaDeNotificacion> obtenerPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public PreferenciaDeNotificacion guardar(PreferenciaDeNotificacion pref) {
        return repo.save(pref);
    }

    public void actualizarPreferencias(Long clienteId, List<PreferenciaDeNotificacion> nuevas) {
        List<PreferenciaDeNotificacion> actuales = repo.findByClienteId(clienteId);
        repo.deleteAll(actuales);
        repo.saveAll(nuevas);
    }

    public void crearPorDefecto(Long clienteId) {
        PreferenciaDeNotificacion pref = new PreferenciaDeNotificacion(clienteId, 1L, true); // 1 = email
        repo.save(pref);
    }
}