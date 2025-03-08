package co.edu.uniquindio.pos_resturant_app.dto.ingrediente;

import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import jakarta.validation.constraints.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;


public record IngredienteCreateDTO(

        @NotNull @Size(min = 1) String nombre,
        @NotNull @Size(min = 1) String marca,
        @NotNull @Positive BigDecimal precioCompra,
        @NotNull @PositiveOrZero Integer cantidadDisponible,
        @NotNull @Length(min = 0, max = 8) String unidad_medida
        //Hace referencia a la columna "notacion" de la tabla "unidad_medida"

) implements DTO<Ingrediente> {


    @Override
    public Ingrediente toEntity() {
        return Ingrediente.builder()
                .nombre(nombre)
                .marca(marca)
                .precioCompra(precioCompra)
                .cantidadDisponible(cantidadDisponible)
                .build();
    }

    @Override
    public String toJSON() {
        return "{" +
                "\"nombre\":\"" + nombre + '\"' +
                ", \"marca\":\"" + marca + '\"' +
                ", \"precioCompra\":" + precioCompra +
                ", \"cantidadDisponible\":" + cantidadDisponible +
                ", \"unidad_medida\":\"" + unidad_medida + '\"' +
                '}';
    }


}
