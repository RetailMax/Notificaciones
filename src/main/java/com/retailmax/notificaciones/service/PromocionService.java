package com.retailmax.notificaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailmax.notificaciones.model.Promocion;
import com.retailmax.notificaciones.repository.PromocionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    public List<Promocion> listarTodas() {
        return promocionRepository.findAll();
    }

    public Promocion guardar(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    public Optional<Promocion> buscarPorId(Long id) {
        return promocionRepository.findById(id);
    }

    public void eliminar(Long id) {
        promocionRepository.deleteById(id);
    }

    public List<Promocion> findAll() {
    
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public Promocion save(Promocion promocion) {
        
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}