package com.retailmax.notifications;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.repository.PedidoRepository;
import com.retailmax.notifications.repository.UsuarioRepository;

import com.retailmax.notificaciones.model.Promocion;
import com.retailmax.notificaciones.repository.PromocionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        Pedido.EstadoPedido[] estados = Pedido.EstadoPedido.values();

        // Verificar si ya existen pedidos para evitar duplicados
        long count = pedidoRepository.count();
        if (count == 0) {
            // Solo crear pedidos si no existen
            for (int i = 0; i < 15; i++) {
                String codigoId = "PED-" + (1000 + i);
                // Verificar si el pedido ya existe
                boolean existe = pedidoRepository.findAll().stream()
                        .anyMatch(p -> codigoId.equals(p.getCodigoId()));
                if (!existe) {
                    Pedido pedido = new Pedido();
                    pedido.setCodigoId(codigoId);
                    pedido.setFechaPedido(LocalDateTime.now().minusDays(random.nextInt(30)));
                    pedido.setEstadoPedido(estados[random.nextInt(estados.length)]);
                    pedido.setMontoTotal(BigDecimal.valueOf(10 + random.nextDouble() * 990).setScale(2, BigDecimal.ROUND_HALF_UP));
                    pedido.setFechaEntrega(LocalDateTime.now().plusDays(random.nextInt(14) + 1));
                    pedido.setUsuario(usuario);
                    pedidoRepository.save(pedido);
                }
            }
            System.out.println("✅ Datos de pedidos y usuario de ejemplo cargados exitosamente en modo desarrollo");
        } else {
            System.out.println("ℹ️ Los datos ya existen en la base de datos. Saltando carga de datos de ejemplo.");
        }

        Random random = new Random();

        // Generar 10 promociones de ejemplo
        for (int i = 0; i < 10; i++) {
            Promocion promocion = new Promocion();
            promocion.setTipo("gmail");
            promocion.setFechaEnvio(LocalDateTime.now().minusDays(random.nextInt(30)));
            promocion.setResultadoEnvio(random.nextBoolean() ? "Enviado" : "Fallido");
            promocionRepository.save(promocion);
    }
}