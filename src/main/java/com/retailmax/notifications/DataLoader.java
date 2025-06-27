package com.retailmax.notifications;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.repository.PedidoRepository;
import com.retailmax.notifications.repository.UsuarioRepository;

import java.util.Date;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Métodos para inyección en pruebas
    void setPedidoRepository(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear un usuario de ejemplo si no existe
        String nroIdEjemplo = "USR-0001";
        Usuario usuario = usuarioRepository.findByNroId(nroIdEjemplo)
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setNroId(nroIdEjemplo);
                    nuevo.setNombre("Usuario Ejemplo");
                    nuevo.setCorreoElectronico("ejemplo@correo.com");
                    nuevo.setEstado("ACTIVO");
                    return usuarioRepository.save(nuevo);
                });

        Random random = new Random();
        String[] estados = {"PENDIENTE", "ENVIADO", "ENTREGADO", "CANCELADO"};

        // Verificar si ya existen pedidos para evitar duplicados
        long count = pedidoRepository.count();
        if (count == 0) {
            // Solo crear pedidos si no existen
            for (int i = 0; i < 15; i++) {
                String codigoId = "PED-" + (1000 + i);
                
                // Verificar si el pedido ya existe
                if (!pedidoRepository.findByCodigoId(codigoId).isPresent()) {
                    Pedido pedido = new Pedido();
                    pedido.setCodigoId(codigoId);
                    pedido.setFechaPedido(new Date(System.currentTimeMillis() - random.nextInt(30) * 86400000L));
                    pedido.setEstadoPedido(estados[random.nextInt(estados.length)]);
                    pedido.setMontoTotal(String.format("%.2f", 10 + random.nextDouble() * 990));
                    pedido.setFechaEntrega(new Date(System.currentTimeMillis() + (random.nextInt(14) + 1) * 86400000L));
                    pedido.setUsuario(usuario);
                    pedidoRepository.save(pedido);
                }
            }
            System.out.println("✅ Datos de pedidos y usuario de ejemplo cargados exitosamente en modo desarrollo");
        } else {
            System.out.println("ℹ️ Los datos ya existen en la base de datos. Saltando carga de datos de ejemplo.");
        }
    }
}