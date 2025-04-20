package co.edu.uniquindio.pos_resturant_app.dto.orden;

import jakarta.validation.constraints.NotNull;

public record PlatoCantidad(
        @NotNull String nombre,
        @NotNull Integer cantidad) {
}
