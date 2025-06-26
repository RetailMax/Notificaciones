package com.retailmax.notifications.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.repository.PedidoRepository;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAll() {
        logger.debug("Buscando todos los pedidos");
        List<Pedido> pedidos = pedidoRepository.findAll();
        logger.info("Se encontraron {} pedidos", pedidos.size());
        return pedidos;
    }

    public Optional<Pedido> findById(Long id) {
        logger.debug("Buscando pedido con ID: {}", id);
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            logger.info("Pedido encontrado con ID: {}", id);
        } else {
            logger.warn("No se encontró pedido con ID: {}", id);
        }
        return pedido;
    }

    public Pedido save(Pedido pedido) {
        logger.debug("Guardando pedido: {}", pedido);
        Pedido savedPedido = pedidoRepository.save(pedido);
        logger.info("Pedido guardado exitosamente con ID: {}", savedPedido.getPedidoId());
        return savedPedido;
    }

    public void deleteById(Long id) {
        logger.debug("Eliminando pedido con ID: {}", id);
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            logger.info("Pedido eliminado exitosamente con ID: {}", id);
        } else {
            logger.warn("No se pudo eliminar pedido con ID: {} - no existe", id);
        }
    }
    
    public boolean existsById(Long id) {
        return pedidoRepository.existsById(id);
    }
}