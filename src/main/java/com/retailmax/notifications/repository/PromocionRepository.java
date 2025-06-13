package com.retailmax.notifications.repository;

import com.retailmax.notifications.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}