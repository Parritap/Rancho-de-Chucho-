package co.edu.uniquindio.pos_resturant_app.dto.unidadMedida;

import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;

import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;
import jakarta.validation.constraints.NotBlank;


import static org.springframework.data.projection.EntityProjection.ProjectionType.DTO;

public record UnidadMedidaCreateDTO(

        @NotBlank String notacion,
        @NotBlank String nombre

) implements DTO<UnidadMedida> {


    @Override
    public UnidadMedida toEntity() {
        return UnidadMedida.builder()
                .notacion(notacion)
                .nombre(nombre)
                .build();
    }
}
