package co.edu.uniquindio.pos_resturant_app.model.keys;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class IngredientePlatoID implements Serializable {
    private Integer idPlato;
    private Integer idIngrediente;
}
