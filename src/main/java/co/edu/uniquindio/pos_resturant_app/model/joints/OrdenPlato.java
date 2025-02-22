package co.edu.uniquindio.pos_resturant_app.model.joints;

import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "orden_plato")
public class OrdenPlato implements Serializable {

    @EmbeddedId
    private OrdenPlatoID id;

    @ManyToOne
    @MapsId("idOrden")  // Links idPedido from PedidoPlatoId
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;


    @ManyToOne
    @MapsId("idPlato")  // Links idPedido from PedidoPlatoId
    @JoinColumn(name = "id_plato", nullable = false)
    private Plato plato;

    @Column(nullable = false)
    private int cantidad;

}
