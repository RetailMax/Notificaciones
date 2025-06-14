package com.retailmax.notifications;

import org.springframework.context.annotation.Profile;

import org.springframework.stereotype.Component;

import com.retailmax.notifications.model.Promocion;
import com.retailmax.notifications.repository.PromocionRepository;


import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
@Autowired
private PromocionRepository promocionRepository;
    public DataLoader(PromocionRepository promocionRepository) {
    }

      @Override
    public void run(String... args) throws Exception {
      
        Random random = new Random();

        // Generar 10 promociones de ejemplo
        for (int i = 0; i < 10; i++) {
            Promocion promocion = new Promocion();
            promocion.setTipo("gmail");
            promocion.setFechaEnvio(LocalDateTime.now().minusDays(random.nextInt(30)));
            promocion.setResultadoEnvio(random.nextBoolean() ? "Enviado" : "Fallido");
            promocionRepository.save(promocion);
        }
    }
}