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
import java.util.Optional;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

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
                    return usuarioRepository.save(nuevo);
                });

        Random random = new Random();
        String[] estados = {"PENDIENTE", "ENVIADO", "ENTREGADO", "CANCELADO"};

        for (int i = 0; i < 15; i++) {
            Pedido pedido = new Pedido();
            pedido.setCodigoId("PED-" + (1000 + i));
            pedido.setFechaPedido(new Date(System.currentTimeMillis() - random.nextInt(30) * 86400000L));
            pedido.setEstadoPedido(estados[random.nextInt(estados.length)]);
            pedido.setMontoTotal(String.format("%.2f", 10 + random.nextDouble() * 990));
            pedido.setFechaEntrega(new Date(System.currentTimeMillis() + (random.nextInt(14) + 1) * 86400000L));
            pedido.setUsuario(usuario);
            pedidoRepository.save(pedido);
        }
        System.out.println("✅ Datos de pedidos y usuario de ejemplo cargados exitosamente en modo desarrollo");
    }
}