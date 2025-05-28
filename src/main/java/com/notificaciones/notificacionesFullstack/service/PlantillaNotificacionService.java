package com.notificaciones.notificacionesFullstack.service;

import com.notificaciones.notificacionesFullstack.model.PlantillaNotificacion;
import com.notificaciones.notificacionesFullstack.repository.PlantillaNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantillaNotificacionService {

    @Autowired
    private PlantillaNotificacionRepository repository;

    public PlantillaNotificacion guardar(PlantillaNotificacion plantilla) {
        return repository.save(plantilla);
    }

    public List<PlantillaNotificacion> listarTodas() {
        return repository.findAll();
    }

    public PlantillaNotificacion buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}