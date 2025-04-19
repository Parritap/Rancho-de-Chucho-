package co.edu.uniquindio.pos_resturant_app.dto.unidadMedida;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UnidadMedidaReadDTO(
        @NotNull Integer id,
        @NotNull String notacion,
        @NotNull String nombre


) {

}
