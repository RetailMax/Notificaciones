package com.retailmax.notifications.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Recupera todos los usuarios
     * @return Lista de usuarios
     */
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado
     * @throws EntityNotFoundException si el usuario no existe
     */
    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Busca un usuario por su número de identificación
     * @param nroId Número de identificación
     * @return Usuario encontrado
     * @throws EntityNotFoundException si el usuario no existe
     */
    @Transactional(readOnly = true)
    public Usuario findByNroId(String nroId) {
        return usuarioRepository.findByNroId(nroId)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con Nro ID: " + nroId));
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente
     * @param usuario Usuario a guardar
     * @return Usuario guardado
     * @throws DuplicateKeyException si el nroId o email ya existe
     */
    public Usuario save(Usuario usuario) {
        // Verificar duplicados solo para nuevos usuarios
        if (usuario.getId() == null) {
            if (usuarioRepository.existsByNroId(usuario.getNroId())) {
                throw new DuplicateKeyException("Ya existe un usuario con el Nro ID: " + usuario.getNroId());
            }
            if (usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
                throw new DuplicateKeyException("Ya existe un usuario con el email: " + usuario.getCorreoElectronico());
            }
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza un usuario existente
     * @param id ID del usuario a actualizar
     * @param usuarioDetails Nuevos datos del usuario
     * @return Usuario actualizado
     * @throws EntityNotFoundException si el usuario no existe
     */
    public Usuario update(Long id, Usuario usuarioDetails) {
        Usuario usuario = findById(id);
        
        // Verificar duplicados en actualización
        if (!usuario.getCorreoElectronico().equals(usuarioDetails.getCorreoElectronico()) &&
            usuarioRepository.existsByCorreoElectronico(usuarioDetails.getCorreoElectronico())) {
            throw new DuplicateKeyException("Ya existe un usuario con el email: " + usuarioDetails.getCorreoElectronico());
        }
        
        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setCorreoElectronico(usuarioDetails.getCorreoElectronico());
        usuario.setEstado(usuarioDetails.getEstado());
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @throws EntityNotFoundException si el usuario no existe
     */
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Cambia el estado de un usuario
     * @param id ID del usuario
     * @param estado Nuevo estado
     * @return Usuario actualizado
     * @throws EntityNotFoundException si el usuario no existe
     */
    public Usuario cambiarEstado(Long id, String estado) {
        Usuario usuario = findById(id);
        usuario.setEstado(estado);
        return usuarioRepository.save(usuario);
    }
}