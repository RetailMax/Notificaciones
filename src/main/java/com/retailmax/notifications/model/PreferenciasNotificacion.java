package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Preferencias_Notificacion")
@Data
@NoArgsConstructor
public class PreferenciasNotificacion {
    @Id
    private Long userId;

    private boolean email = true; // Por defecto activado
    private boolean sms = false;

    private String canalRecomendado = "email";
}