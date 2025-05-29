package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar por nombre o dirección
    // List<EmailNotification> findByNombre(String nombre);
    // List<EmailNotification> findByDireccion(String direccion);

}
