package com.retailmax.notifications.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.retailmax.notifications.model.NotificacionConfirmacionPedido;
import com.retailmax.notifications.repository.NotificacionConfirmacionPedidoRepository;

@Service
public class NotificacionConfirmacionPedidoService {

    @Autowired
    private NotificacionConfirmacionPedidoRepository repositorio;

    public List<NotificacionConfirmacionPedido> buscarTodas() {
        return repositorio.findAll();
    }

    public NotificacionConfirmacionPedido enviar(NotificacionConfirmacionPedido notificacion) {
        // Aquí podrías agregar integración con un proveedor de correo electrónico
        return repositorio.save(notificacion);
    }
}