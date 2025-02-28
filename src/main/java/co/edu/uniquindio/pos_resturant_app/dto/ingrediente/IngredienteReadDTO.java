package co.edu.uniquindio.pos_resturant_app.dto.ingrediente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record IngredienteReadDTO(

        @NotNull Integer id,
        @NotNull String nombre,
        @NotNull String marca,
        @NotNull @Size(min = 0) BigDecimal precioCompra,
        @NotNull @Size(min = 0) Integer cantidadDisponible,
        @NotNull @Length(min = 0, max = 8) String unidad_medida //Hace referencia a la columna "notacion" de la tabla "unidad_medida"

) {
}
