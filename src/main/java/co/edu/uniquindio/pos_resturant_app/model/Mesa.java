package co.edu.uniquindio.pos_resturant_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor // Necesario para que Spring pueda instanciar la clase.
@AllArgsConstructor
@SuperBuilder
public class Mesa implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(nullable = false)
    private boolean disponible;

    @OneToMany (mappedBy = "mesa")
    private List<Orden> mesas;
}
