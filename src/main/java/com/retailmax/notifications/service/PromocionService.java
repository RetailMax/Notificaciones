package com.retailmax.notifications.service;

import com.retailmax.notifications.model.Promocion;
import com.retailmax.notifications.repository.PromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}