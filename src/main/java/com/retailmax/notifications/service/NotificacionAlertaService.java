package com.retailmax.notifications.service;



import com.retailmax.notifications.model.NotificacionAlerta;
import com.retailmax.notifications.repository.NotificacionAlertaRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class NotificacionAlertaService {

    @Autowired
    private NotificacionAlertaRepository notificacionAlertaRepository;

    // CREATE o UPDATE
    public NotificacionAlerta save(NotificacionAlerta alerta) {
        return notificacionAlertaRepository.save(alerta);
    }

    // READ
    public List<NotificacionAlerta> findAll() {
        return notificacionAlertaRepository.findAll();
    }

    // Existe o no
    public boolean existsById(Long id) {
        return notificacionAlertaRepository.existsById(id);
    }

    // Count
    public long count() {
        return notificacionAlertaRepository.count();
    }

    // DELETE
    public void deleteAll() {
        notificacionAlertaRepository.deleteAll();
    }
  }

