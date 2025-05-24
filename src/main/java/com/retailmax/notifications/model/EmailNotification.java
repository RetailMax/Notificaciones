package com.retailmax.notifications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EmailNotification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column()
    private String nombre;

    @Column()
    private String correo;

    @Column()
    private String nroOrden;
}
