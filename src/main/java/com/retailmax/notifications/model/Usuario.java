package com.retailmax.notifications.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Modelo de Usuario del sistema RetailMax
 */
@Schema(description = "Modelo de Usuario del sistema RetailMax")
public class Usuario {

    /** ID único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    /** Número de identificación único del usuario */
    @NotBlank(message = "El número de identificación es obligatorio")
    @Size(min = 5, max = 20, message = "El número de identificación debe tener entre 5 y 20 caracteres")
    @Column(name = "nro_id", unique = true, length = 20)
    @Schema(description = "Número de identificación único del usuario", example = "12345678", required = true)
    private String nroId;

    /** Nombre completo del usuario */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(length = 50)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    private String nombre;

    /** Correo electrónico único del usuario */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Column(name = "correo_electronico", unique = true, length = 100)
    @Schema(description = "Correo electrónico único del usuario", example = "juan.perez@email.com", required = true)
    private String correoElectronico;

    /** Estado del usuario en el sistema */
    @Column(name = "estado", length = 20)
    @Schema(description = "Estado del usuario en el sistema", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO"})
    private String estado = "ACTIVO";  // Valor por defecto

    // Relación con Pedidos (un usuario puede tener múltiples pedidos)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de pedidos asociados al usuario", hidden = true)
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