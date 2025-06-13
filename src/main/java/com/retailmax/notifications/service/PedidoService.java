package com.retailmax.notifications.service;


import org.springframework.stereotype.Service;
import java.util.List;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository repositorio;


     public PedidoService(PedidoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Pedido> buscarTodas() {
        return repositorio.findAll();
    }

    public Pedido guardar(Pedido pedido) {
        
        return repositorio.save(pedido);
    }

}
