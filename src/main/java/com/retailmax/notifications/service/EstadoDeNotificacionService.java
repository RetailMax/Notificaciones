package com.retailmax.notifications.service;

import com.retailmax.notifications.model.EstadoDeNotificacion;
import com.retailmax.notifications.repository.EstadoDeNotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoDeNotificacionService {

    private final EstadoDeNotificacionRepository repository;

    public EstadoDeNotificacionService(EstadoDeNotificacionRepository repository) {
        this.repository = repository;
    }

    public List<EstadoDeNotificacion> findAll() {
        return repository.findAll();
    }

    public Optional<EstadoDeNotificacion> findById(Long id) {
        return repository.findById(id);
    }

    public EstadoDeNotificacion save(EstadoDeNotificacion estado) {
        return repository.save(estado);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EstadoDeNotificacion findByDescripcion(String descripcion) {
        return repository.findByDescripcion(descripcion);
    }
}