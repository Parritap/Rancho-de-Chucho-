package co.edu.uniquindio.pos_resturant_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Ingrediente implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (nullable = false)
    private String nombre;

    private String marca;

    @Column (nullable = false)
    private BigDecimal precioCompra ;

    @Column (nullable = false)
    @Min( value = 0 )
    private int cantidadDisponible;

    @ManyToOne
    @JoinColumn (nullable = false, name = "unidad_medida", referencedColumnName = "notacion")
    private UnidadMedida unidadMedida;

    public String toJSON () {
        return "{" +
                "\"id\":" + id +
                ", \"nombre\":\"" + nombre + '\"' +
                ", \"marca\":\"" + marca + '\"' +
                ", \"precioCompra\":" + precioCompra +
                ", \"cantidadDisponible\":" + cantidadDisponible +
                ", \"unidad_medida\":\"" + unidadMedida.getNotacion() + '\"' +
                '}';
    }
}
