package co.edu.uniquindio.pos_resturant_app.model.keys;


import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor

public class PedidoPlatoID {
    private int idPedido;
    private int idPlato;
}
