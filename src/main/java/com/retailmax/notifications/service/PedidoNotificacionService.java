package com.retailmax.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailmax.notifications.repository.PedidoNotificacionRepository;
import com.retailmax.notifications.model.PedidoNotificacion;
import java.util.List;
import java.util.Optional;



@Service
public class PedidoNotificacionService {

    @Autowired
    private PedidoNotificacionRepository pedidoNotificacionRepository;


    public List<PedidoNotificacion> findAll() {
        return pedidoNotificacionRepository.findAll();
    }

    public Optional<PedidoNotificacion> findById(Integer id) {
        return pedidoNotificacionRepository.findById(id);
    }

    public PedidoNotificacion save(PedidoNotificacion pedidoNotificacion) {
        return pedidoNotificacionRepository.save(pedidoNotificacion);
    }

    public void deleteById(Integer id) {
        pedidoNotificacionRepository.deleteById(id);
    }

}
