package com.notificaciones.notificacionesFullstack.service;

import com.notificaciones.notificacionesFullstack.model.NotificacionPago;
import com.notificaciones.notificacionesFullstack.repository.NotificacionPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionPagoService {

    @Autowired
    private NotificacionPagoRepository notificacionPagoRepository;

    public NotificacionPago guardar(NotificacionPago notificacion) {
        if (notificacion.getEstado() == null) {
            notificacion.setEstado("EN_COLA");
        }
        return notificacionPagoRepository.save(notificacion);
    }

    public List<NotificacionPago> listarTodas() {
        return notificacionPagoRepository.findAll();
    }

    // Método para actualizar el estado de una notificación con mensajes explícitos
    public String actualizarEstado(Integer idUsuario, String nuevoEstado) {
        NotificacionPago notificacion = notificacionPagoRepository.findById(idUsuario).orElse(null);
        if (notificacion != null) {
            notificacion.setEstado(nuevoEstado);
            notificacionPagoRepository.save(notificacion);
            switch (nuevoEstado) {
                case "ENVIADA":
                    return "Notificación enviada correctamente";
                case "FALLIDA":
                    return "No se pudo enviar la notificación";
                case "EN_COLA":
                    return "La notificación está en cola de procesamiento";
                default:
                    return "Estado actualizado";
            }
        }
        return "Notificación no encontrada";
    }
}