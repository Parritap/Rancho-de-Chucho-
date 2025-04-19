package co.edu.uniquindio.pos_resturant_app.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record UsuarioReadDTO(

        @NotNull String cedula,
        @NotNull String nombre,
        @NotNull String username
) {
}
