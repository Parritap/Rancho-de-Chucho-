package co.edu.uniquindio.pos_resturant_app.dto.tipoPlato;

import co.edu.uniquindio.pos_resturant_app.model.TipoPlato;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TipoPlatoCreateDTO(
        @NotNull String nombre,
        @NotNull String descripcion,
        Integer idTipoPadre
) {

    public TipoPlato toEntity() {
        return TipoPlato.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .build();
    }
}

