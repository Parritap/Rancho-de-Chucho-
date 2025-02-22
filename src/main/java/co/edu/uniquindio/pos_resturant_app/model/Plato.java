package co.edu.uniquindio.pos_resturant_app.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private int id_plato;

    @Column (nullable = false)
    private String nombre;

    private String descripcion;

    @Column (nullable = false)
    private double precio;

}
