package co.edu.uniquindio.pos_resturant_app.dto.ingrediente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record IngredienteReadDTO(

        @NotNull Integer id,
        @NotNull @Size(min = 1) String nombre,
        @NotNull @Size(min = 1) String marca,
        @NotNull @Positive BigDecimal precioCompra,
        @NotNull @PositiveOrZero Integer cantidadDisponible,
        @NotNull @Length(min = 0, max = 8) String unidad_medida
        //Hace referencia a la columna "notacion" de la tabla "unidad_medida"

) {
}
