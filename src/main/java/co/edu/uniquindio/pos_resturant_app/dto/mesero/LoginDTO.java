package co.edu.uniquindio.pos_resturant_app.dto.mesero;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull String username,
        @NotNull String password
) {
}

