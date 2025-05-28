package com.notificaciones.notificacionesFullstack.service;

import com.notificaciones.notificacionesFullstack.model.PreferenciaNotificacion;
import com.notificaciones.notificacionesFullstack.repository.PreferenciaNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferenciaNotificacionService {

    @Autowired
    private PreferenciaNotificacionRepository repository;

    public PreferenciaNotificacion guardar(PreferenciaNotificacion pref) {
        return repository.save(pref);
    }

    public Optional<PreferenciaNotificacion> buscarPorId(Integer idUsuario) {
        return repository.findById(idUsuario);
    }

    public void eliminar(Integer idUsuario) {
        repository.deleteById(idUsuario);
    }

    public PreferenciaNotificacion actualizarParcial(Integer idUsuario, PreferenciaNotificacion cambios) {
        return repository.findById(idUsuario).map(pref -> {
            if (cambios.getEmail() != null) pref.setEmail(cambios.getEmail());
            if (cambios.getSms() != null) pref.setSms(cambios.getSms());
            return repository.save(pref);
        }).orElse(null);
    }
}