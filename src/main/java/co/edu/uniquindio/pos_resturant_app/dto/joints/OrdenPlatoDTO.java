package co.edu.uniquindio.pos_resturant_app.dto.joints;

import jakarta.validation.constraints.NotNull;

public record OrdenPlatoDTO(
        @NotNull Integer idOrden,
        @NotNull Integer idPlato,
        @NotNull Integer cantidad
) {
}
