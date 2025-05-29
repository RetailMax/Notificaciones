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
    String nombre;//nombre de la notificacion o alerta

    @Column()
    String descripcion; //descripcion de la notificacion o alerta

    @Column()
    String tipo;//tipo de notificacion o alerta (ejemplo: email, sms)

    @Column()
    String estado;//estado de la notificacion o alerta (ejemplo: activa, inactiva)

    @Column()
    String fechaCreacion;//fecha de creacion de notificacion o alerta

    @Column()
    String fechaModificacion;//fecha de la ultima modificacion de la notificacion o alerta
}


