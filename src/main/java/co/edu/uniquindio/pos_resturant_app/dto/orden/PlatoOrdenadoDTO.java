package co.edu.uniquindio.pos_resturant_app.dto.orden;

import co.edu.uniquindio.pos_resturant_app.model.Plato;

public record PlatoOrdenadoDTO(
        String nombre,
        int cantidad,
        double precioUnitario
) {

    PlatoOrdenadoDTO toDTO(Plato plato, int cantidad) {
        return new PlatoOrdenadoDTO(
                plato.getNombre(),
                cantidad,
                plato.getPrecio()
        );
    }

}
