package co.edu.uniquindio.pos_resturant_app.model;

import co.edu.uniquindio.pos_resturant_app.model.keys.OrdenPlatoID;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "pedido_plato")
public class OrdenPlato implements Serializable {

    @Embedded
    private OrdenPlatoID id;
}
