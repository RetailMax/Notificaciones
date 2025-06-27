package com.retailmax.notifications.service;

import com.retailmax.notifications.model.Notificacion;
import com.retailmax.notifications.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public Notificacion registrar(Notificacion notificacion) {
        // Aquí puedes agregar lógica para notificar a soporte si resultado_envio es ERROR
        return repository.save(notificacion);
    }

    public List<Notificacion> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<Notificacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Notificacion> obtenerPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }
}