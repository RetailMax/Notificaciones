package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciasDeNotificacion;
import com.retailmax.notifications.repository.RepositoryPreferenciasDeNotificacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePreferenciasDeNotificacion {

    private final RepositoryPreferenciasDeNotificacion repo;

    public ServicePreferenciasDeNotificacion(RepositoryPreferenciasDeNotificacion repo) {
        this.repo = repo;
    }

    public List<PreferenciasDeNotificacion> obtenerPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public PreferenciasDeNotificacion guardar(PreferenciasDeNotificacion pref) {
        return repo.save(pref);
    }

    public void actualizarPreferencias(Long clienteId, List<PreferenciasDeNotificacion> nuevas) {
        List<PreferenciasDeNotificacion> actuales = repo.findByClienteId(clienteId);
        repo.deleteAll(actuales);
        repo.saveAll(nuevas);
    }

    public void crearPorDefecto(Long clienteId) {
        PreferenciasDeNotificacion pref = new PreferenciasDeNotificacion(clienteId, 1L, true); // 1 = email
        repo.save(pref);
    }
}
