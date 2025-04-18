package co.edu.uniquindio.pos_resturant_app.dto.tipoPlato;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TipoPlatoReadDTO(
        @NotNull Integer id,
        @NotNull String nombre,
        @NotNull String descripcion,
        @NotNull Integer idTipoPadre

) {
}
