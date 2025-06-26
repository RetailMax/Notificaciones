package com.retailmax.notifications;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.model.EstadoPedido;
import com.retailmax.notifications.repository.PedidoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    public DataLoader(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        Random random = new Random();

        // Generar 15 pedidos de ejemplo
        for (int i = 0; i < 15; i++) {
            Pedido pedido = new Pedido();
            
            // Cliente ID aleatorio entre 1 y 100
            pedido.setClienteId((long) (random.nextInt(100) + 1));
            
            // Fecha de pedido aleatoria en los últimos 30 días
            LocalDateTime fechaPedido = LocalDateTime.now().minusDays(random.nextInt(30));
            pedido.setFechaPedido(fechaPedido);
            
            // Estado aleatorio
            EstadoPedido[] estados = EstadoPedido.values();
            pedido.setEstadoPedido(estados[random.nextInt(estados.length)]);
            
            // Total aleatorio entre 10.00 y 1000.00
            BigDecimal total = new BigDecimal(random.nextDouble() * 990 + 10).setScale(2, BigDecimal.ROUND_HALF_UP);
            pedido.setTotal(total);
            
            // Fecha de entrega estimada (entre 1 y 14 días después del pedido)
            LocalDateTime fechaEntregaEstimada = fechaPedido.plusDays(random.nextInt(14) + 1);
            pedido.setFechaEntregaEstimada(fechaEntregaEstimada);
            
            pedidoRepository.save(pedido);
        }
        
        System.out.println("✅ Datos de pedidos cargados exitosamente en modo desarrollo");
    }
} 