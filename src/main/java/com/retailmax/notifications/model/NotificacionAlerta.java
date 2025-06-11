package com.retailmax.notifications.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //esta clase es una entidad
@Table(name = "NotificacionAlerta")//nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionAlerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
     @Column()
    String nombre; // nombre de la notificación o alerta

    @Column()
    String descripcion; // descripción de la notificación o alerta

    @Column()
    String tipo = "gmail"; // tipo de notificación, siempre gmail

    @Column()
    String estado; // estado de la notificación o alerta (ejemplo: activa, inactiva)

    @Column()
    String fechaCreacion; // fecha de creación de notificación o alerta

    @Column()
    String fechaModificacion; // fecha de la última modificación de la notificación o alerta
}



