package co.edu.uniquindio.pos_resturant_app.model.keys;


import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor

public class OrdenPlatoID {
    private int idOrden;
    private int idPlato;
}
