package co.edu.uniquindio.pos_resturant_app.dto.joints;

import jakarta.validation.constraints.NotNull;

/**
 * Esta clase representa un ingrediente atomico, es decir, un ingrediente con su
 * respectiva cantidad que se encuentra en una receta para un plato.
 *
 * @param idIngrediente
 * @param notacionUnidadMedida Primary key de la tabla unidad_medida
 * @param cantidad Flotante que representa la cantidad del ingrediente
 */
public record IngredienteAtom(
        @NotNull Integer idIngrediente,
        @NotNull String notacionUnidadMedida, //es decir, el primary key.
        @NotNull Float cantidad
) {
}
