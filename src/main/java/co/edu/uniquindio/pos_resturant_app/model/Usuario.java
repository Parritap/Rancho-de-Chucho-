package co.edu.uniquindio.pos_resturant_app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@Setter
@NoArgsConstructor // Necesario para que Spring pueda instanciar la clase.
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public abstract class Usuario implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    private String cedula;
    @Column(nullable = false)
    private String nombre;
    @Column(unique = true, nullable = false)
    private String username;
    @Column (nullable = false)
    private String password;
    @Column (name = "img_url")
    private String imgURL;
    @Column (name = "esta_activo")
    private boolean estaActivo;
}
