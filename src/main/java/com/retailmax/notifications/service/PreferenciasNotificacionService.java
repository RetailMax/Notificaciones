package com.retailmax.notifications.service;

import com.retailmax.notifications.model.PreferenciasNotificacion;
import com.retailmax.notifications.repository.PreferenciasNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenciasNotificacionService {

    @Autowired
    private PreferenciasNotificacionRepository repository;

    public PreferenciasNotificacion obtenerPorUserId(Long userId) {
        return repository.findById(userId)
                .orElseGet(() -> {
                    PreferenciasNotificacion p = new PreferenciasNotificacion();
                    p.setUserId(userId);
                    p.setEmail(true);  // Valor predeterminado
                    p.setSms(false);
                    p.setCanalRecomendado("email");
                    return repository.save(p);
                });
    }

    public PreferenciasNotificacion actualizar(Long userId, PreferenciasNotificacion nuevas) {
        PreferenciasNotificacion actual = obtenerPorUserId(userId);

        // Si ambos canales están desactivados, activar email por defecto
        if (!nuevas.isEmail() && !nuevas.isSms()) {
            nuevas.setEmail(true);
        }

        // Permitir que uno o ambos canales estén activos
        actual.setEmail(nuevas.isEmail());
        actual.setSms(nuevas.isSms());
        actual.setCanalRecomendado(nuevas.getCanalRecomendado());

        return repository.save(actual);
    }
}