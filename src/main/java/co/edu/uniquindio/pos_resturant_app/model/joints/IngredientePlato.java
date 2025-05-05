package co.edu.uniquindio.pos_resturant_app.model.joints;

import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;
import co.edu.uniquindio.pos_resturant_app.model.keys.IngredientePlatoID;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Entity
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Table(name = "ingrediente_plato")
public class IngredientePlato implements Serializable {

    @EmbeddedId
    private IngredientePlatoID id;

    @ManyToOne
    @MapsId("idIngrediente")
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @ManyToOne
    @MapsId("idPlato")
    @JoinColumn(name = "id_plato", nullable = false)
    private Plato plato;

    @Min(value = 0)
    @Column(nullable = false)
    @NotNull
    private Float cantidad;

    @ManyToOne
    @JoinColumn (name = "id_unidad_medida", referencedColumnName = "notacion" , nullable = false)
    private UnidadMedida unidadMedida;


//    @ManyToOne
//    @NotNull
//    @JoinColumn(name = "unidad", nullable = false) // Foreign key to Unidad table
//    private UnidadMedida unidad; // Assuming you have a Unidad entity

}
