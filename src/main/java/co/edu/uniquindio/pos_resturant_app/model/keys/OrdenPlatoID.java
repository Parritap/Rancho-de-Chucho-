package co.edu.uniquindio.pos_resturant_app.model.keys;


import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode

public class OrdenPlatoID {
    private int idOrden;
    private int idPlato;

}
