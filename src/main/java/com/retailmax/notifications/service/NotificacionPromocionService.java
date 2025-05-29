package com.retailmax.notifications.service;
import com.retailmax.notifications.model.NotificacionPromocion;
import com.retailmax.notifications.repository.NotificacionPromocionRepository;


import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
@Service
@Transactional
public class NotificacionPromocionService {

    @Autowired
    private NotificacionPromocionRepository notificacionPromocionRepository;

    // Guardar o actualizar
    public NotificacionPromocion save(NotificacionPromocion promocion) {
        return notificacionPromocionRepository.save(promocion);
    }

    // Listar todas
    public List<NotificacionPromocion> findAll() {
        return notificacionPromocionRepository.findAll();
    }

   
    }
