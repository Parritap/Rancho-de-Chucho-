package co.edu.uniquindio.pos_resturant_app.dto.plato;

import co.edu.uniquindio.pos_resturant_app.dto.joints.IngredienteAtom;
import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PlatoCreateDTO(

        @NotNull String nombre,
        @NotNull String descripcion,
        @NotNull Double precio,
        @NotNull Integer id_tipo_plato,
        @NotNull List<IngredienteAtom> listaIngredientes


) implements DTO<Plato> {

    /**
     * Lacks tipoPlato reference
     *
     * @return Plato with nombre, descripcion y precio
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
                ", \"id_tipo_plato\":" + id_tipo_plato +
                ", \"listaIngredientes\":" + listaIngredientes +
                '}';
    }


}
