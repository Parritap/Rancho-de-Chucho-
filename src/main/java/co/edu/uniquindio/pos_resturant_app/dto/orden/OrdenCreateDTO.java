package co.edu.uniquindio.pos_resturant_app.dto.orden;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrdenCreateDTO(
        @NotNull Integer idMesa,
        @NotNull String cedulaMesero,
        @NotNull List<PlatilloCantidadDTO> platillos //Record anidado para recibir la lista de platillos
) {
    public record PlatilloCantidadDTO(
            @NotNull String nombre,
            @NotNull Integer cantidad
    ) {}
}
