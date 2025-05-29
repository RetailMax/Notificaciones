package com.retailmax.notifications.repository;
//import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.retailmax.notifications.model.NotificacionPromocion;


public interface NotificacionPromocionRepository extends JpaRepository<NotificacionPromocion, Long> {
   List<NotificacionPromocion> findByMensaje(String mensaje); // Busca las promociones  por nombr


    

}
