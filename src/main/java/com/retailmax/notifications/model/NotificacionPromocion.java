package com.retailmax.notifications.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "NotificacionPromocion")//nombre de la tabla en la base de datos
public class NotificacionPromocion {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
// Mensaje personalizado de la promoción
    private String mensaje;

    // Fecha de envío de la notificación
    private LocalDateTime fechaEnvio;


    // Tipo de notificación, siempre gmail
    private String tipo = "gmail";

    // Indica si la notificación fue leída
    private boolean leida = false;
}
