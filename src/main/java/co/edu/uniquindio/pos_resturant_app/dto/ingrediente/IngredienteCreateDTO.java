package co.edu.uniquindio.pos_resturant_app.dto.ingrediente;

import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

import co.edu.uniquindio.pos_resturant_app.interfaces.DTO;

public record IngredienteCreateDTO(

        @NotNull @NotBlank String nombre,
        @NotNull @NotBlank String marca,
        @NotNull @Size(min = 0) BigDecimal precioCompra,
        @NotNull @Size(min = 0) Integer cantidadDisponible,
        @NotNull @NotBlank @Length(min = 1, max = 8) String unidad_medida
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
}
