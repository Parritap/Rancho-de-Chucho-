package co.edu.uniquindio.pos_resturant_app.dto.plato;

import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;
import co.edu.uniquindio.pos_resturant_app.model.Plato;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PlatoReadDTO(
        @NotNull Integer id,
        @NotNull String nombre,
        @NotNull String descripcion,
        @NotNull Double precio,
        @NotNull String tipo_plato,
        @NotEmpty boolean activo) implements DTO<Plato> {
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
                "\"id\":" + id +
                ", \"nombre\":\"" + nombre + '\"' +
                ", \"descripcion\":\"" + descripcion + '\"' +
                ", \"precio\":" + precio +
                ", \"id_tipo_plato\":\"" + tipo_plato + '\"' +
                ", \"activo\":\"" + activo + '\"' +
                '}';
    }
}
