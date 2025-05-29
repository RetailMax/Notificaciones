package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.PedidoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoNotificacionRepository extends JpaRepository<PedidoNotificacion, Integer> {
    
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar por nombre o dirección
    // List<PedidoNotificacion> findByNombre(String nombre);
    // List<PedidoNotificacion> findByDireccion(String direccion);

}
