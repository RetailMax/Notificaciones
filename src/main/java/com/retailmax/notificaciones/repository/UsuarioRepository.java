package com.retailmax.notifications.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.retailmax.notifications.model.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByNroId(String nroId);
    
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    
    boolean existsByNroId(String nroId);
    
    boolean existsByCorreoElectronico(String correoElectronico);
}