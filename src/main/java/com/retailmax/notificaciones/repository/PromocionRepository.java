package com.retailmax.notificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailmax.notificaciones.model.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}