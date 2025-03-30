package co.edu.uniquindio.pos_resturant_app.dto.plato;

import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import jakarta.validation.constraints.NotNull;

public record PlatoCreateDTO(

        @NotNull String nombre,
        @NotNull String descripcion,
        @NotNull Double precio,
        @NotNull Integer id_tipo_plato


) implements DTO<Plato> {

    /**
     * Lacks tipoPlato reference
     * @return Plato woith noombre, descripcion yprecio
     */
    @Override
    public Plato toEntity() {
        return Plato.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .build();
    }

    @Override
    public String toJSON() {
        return "{" +
                "\"nombre\":\"" + nombre + '\"' +
                ", \"descripcion\":\"" + descripcion + '\"' +
                ", \"precio\":" + precio +
                ", \"id_tipo_plato\":\"" + id_tipo_plato + '\"' +
                '}';
    }


}
