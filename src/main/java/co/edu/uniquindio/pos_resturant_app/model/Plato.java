package co.edu.uniquindio.pos_resturant_app.model;


import co.edu.uniquindio.pos_resturant_app.dto.plato.PlatoReadDTO;
import co.edu.uniquindio.pos_resturant_app.model.helpers.helperClass;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Plato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_plato;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private double precio;

    @ManyToOne
    @JoinColumn(
            name = "id_tipo_plato",
            referencedColumnName = "id_tipo_plato",
            nullable = false)
    private TipoPlato tipoPlato;

    @Column
    private boolean activo;


    public PlatoReadDTO toReadDTO() {

        return new PlatoReadDTO(
                id_plato,
                nombre,
                descripcion,
                precio,
                tipoPlato.getNombre()
        );
    }


}
