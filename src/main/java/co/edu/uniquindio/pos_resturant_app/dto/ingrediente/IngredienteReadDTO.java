package co.edu.uniquindio.pos_resturant_app.dto.ingrediente;

import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record IngredienteReadDTO (

        @NotNull Integer id,
        @NotNull @Size(min = 1) String nombre,
        @NotNull @Size(min = 1) String marca,
        @NotNull @Positive BigDecimal precioCompra,
        @NotNull @PositiveOrZero Integer cantidadDisponible,
        @NotNull @Length(min = 1, max = 8) String unidad_medida
        //Hace referencia a la columna "notacion" de la tabla "unidad_medida"

) implements DTO<Ingrediente> {
    @Override
    public Ingrediente toEntity() {
        return Ingrediente.builder()
                .id(id)
                .nombre(nombre)
                .marca(marca)
                .precioCompra(precioCompra)
                .cantidadDisponible(cantidadDisponible)
                .build();
    }

    public IngredienteReadDTO(Ingrediente entity) {
        this(
                entity.getId(),
                entity.getNombre(),
                entity.getMarca(),
                entity.getPrecioCompra(),
                entity.getCantidadDisponible(),
                entity.getUnidadMedida().getNotacion()
        );
    }

    @Override
    public String toJSON() {
        return "{" +
                "\"id\":" + id +
                ", \"nombre\":\"" + nombre + '\"' +
                ", \"marca\":\"" + marca + '\"' +
                ", \"precioCompra\":" + precioCompra +
                ", \"cantidadDisponible\":" + cantidadDisponible +
                ", \"unidad_medida\":\"" + unidad_medida + '\"' +
                '}';
    }

}
