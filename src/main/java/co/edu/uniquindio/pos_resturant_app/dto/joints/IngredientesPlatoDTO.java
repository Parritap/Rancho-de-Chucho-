package co.edu.uniquindio.pos_resturant_app.dto.joints;


import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteDetailDTO;

import java.util.List;

/**
 * Record usado para mostrar los ingredientes de un plato.
 * Por cada plato hay un elemento {@link IngredienteDetailDTO} que posee {id del ingrediente, nombre del ingrediente }
 */
public record IngredientesPlatoDTO(
        List<IngredienteDetailDTO> listaIngredientes
) {
}
