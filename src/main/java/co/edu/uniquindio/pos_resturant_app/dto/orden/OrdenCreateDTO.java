package co.edu.uniquindio.pos_resturant_app.dto.orden;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrdenCreateDTO(

        @NotNull String idMesa,
        @NotNull String cedulaMesero

) {
}
