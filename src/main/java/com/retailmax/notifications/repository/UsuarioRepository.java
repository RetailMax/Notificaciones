package com.retailmax.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.retailmax.notifications.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}