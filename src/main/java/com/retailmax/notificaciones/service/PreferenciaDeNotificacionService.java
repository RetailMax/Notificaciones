package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciaDeNotificacion;
import com.retailmax.notifications.repository.PreferenciaDeNotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreferenciaDeNotificacionService {

    private final PreferenciaDeNotificacionRepository repository;

    public PreferenciaDeNotificacionService(PreferenciaDeNotificacionRepository repository) {
        this.repository = repository;
    }

    public List<PreferenciaDeNotificacion> obtenerPorCliente(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    @Transactional
    public void actualizarPreferencias(Long clienteId, List<PreferenciaDeNotificacion> nuevasPreferencias) {
        // Elimina las preferencias anteriores
        List<PreferenciaDeNotificacion> existentes = repository.findByClienteId(clienteId);
        repository.deleteAll(existentes);

        // Guarda las nuevas preferencias
        repository.saveAll(nuevasPreferencias);
    }
}