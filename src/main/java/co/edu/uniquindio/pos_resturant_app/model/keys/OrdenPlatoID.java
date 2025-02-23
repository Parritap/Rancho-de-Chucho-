package co.edu.uniquindio.pos_resturant_app.model.keys;


import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor

public class OrdenPlatoID {
    private int idOrden;
    private int idPlato;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdenPlatoID that = (OrdenPlatoID) o;
        return Objects.equals(idOrden, that.idOrden) &&
                Objects.equals(idPlato, that.idPlato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrden, idPlato);
    }

}
