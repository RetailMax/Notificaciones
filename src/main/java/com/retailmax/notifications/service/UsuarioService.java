package com.retailmax.notifications.service;


import org.springframework.stereotype.Service;
import java.util.List;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repositorio;

     public UsuarioService(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Usuario> buscarTodas() {
        return repositorio.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        // Aquí también se puede integrar con un proveedor de correo electrónico o notificación push
        return repositorio.save(usuario);
    }
}