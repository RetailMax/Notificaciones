package com.retailmax.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.retailmax.notifications.model.NotificacionActualizacionEstadoPedido;
import com.retailmax.notifications.repository.NotificacionActualizacionEstadoPedidoRepository;

@Service
public class NotificacionActualizacionEstadoPedidoService {

    @Autowired
    private NotificacionActualizacionEstadoPedidoRepository repositorio;

    public List<NotificacionActualizacionEstadoPedido> buscarTodas() {
        return repositorio.findAll();
    }

    public NotificacionActualizacionEstadoPedido enviar(NotificacionActualizacionEstadoPedido notificacion) {
        // Aquí también se puede integrar con un proveedor de correo electrónico o notificación push
        return repositorio.save(notificacion);
    }
}