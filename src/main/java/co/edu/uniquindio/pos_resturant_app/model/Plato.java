package co.edu.uniquindio.pos_resturant_app.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.flogger.Flogger;

import java.io.Serializable;

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

    @Nullable
    @ManyToOne
    @JoinColumn(
            name = "id_tipo_plato",
            referencedColumnName = "id_tipo_plato",
            nullable = true)
    private TipoPlato tipoPlato;

}
