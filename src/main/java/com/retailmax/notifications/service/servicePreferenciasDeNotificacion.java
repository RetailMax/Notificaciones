package com.retailmax.notifications.service;

import com.retailmax.notifications.model.preferenciasDeNotificacion;
import com.retailmax.notifications.repository.repositorypreferenciasDeNotificacion;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class servicePreferenciasDeNotificacion {

    private final repositorypreferenciasDeNotificacion repo;

    public servicePreferenciasDeNotificacion(repositorypreferenciasDeNotificacion repo) {
        this.repo = repo;
    }

    public List<preferenciasDeNotificacion> obtenerPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public preferenciasDeNotificacion guardar(preferenciasDeNotificacion pref) {
        return repo.save(pref);
    }

    public void actualizarPreferencias(Long clienteId, List<preferenciasDeNotificacion> nuevas) {
        List<preferenciasDeNotificacion> actuales = repo.findByClienteId(clienteId);
        repo.deleteAll(actuales);
        repo.saveAll(nuevas);
    }

    public void crearPorDefecto(Long clienteId) {
        preferenciasDeNotificacion pref = new preferenciasDeNotificacion(clienteId, 1L, true); // 1 = email
        repo.save(pref);
    }
}