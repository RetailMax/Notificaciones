package com.retailmax.notifications.service;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import com.retailmax.notifications.repository.repositoryEstadoDeNotificacion;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class serviceEstadoDeNotificacion {

    private final repositoryEstadoDeNotificacion repo;

    public serviceEstadoDeNotificacion(repositoryEstadoDeNotificacion repo) {
        this.repo = repo;
    }

    public EstadoDeNotificacion guardar(EstadoDeNotificacion notificacion) {
        return repo.save(notificacion);
    }

    public List<EstadoDeNotificacion> obtenerPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public Optional<EstadoDeNotificacion> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<EstadoDeNotificacion> obtenerTodas() {
        return repo.findAll();
    }
}