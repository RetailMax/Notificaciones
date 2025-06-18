package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de identificación es obligatorio")
    @Column(name = "nro_id", unique = true, length = 20)
    private String nroId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(length = 50)
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Column(name = "correo_electronico", unique = true, length = 100)
    private String correoElectronico;

    @Column(name = "estado", length = 20)
    private String estado = "ACTIVO";  // Valor por defecto

    // Relación con Pedidos (un usuario puede tener múltiples pedidos)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    // Constructor personalizado para campos obligatorios
    public Usuario(String nroId, String nombre, String correoElectronico) {
        this.nroId = nroId;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    // Método helper para agregar pedidos
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setUsuario(this);
    }

    // Método helper para remover pedidos
    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
        pedido.setUsuario(null);
    }
}