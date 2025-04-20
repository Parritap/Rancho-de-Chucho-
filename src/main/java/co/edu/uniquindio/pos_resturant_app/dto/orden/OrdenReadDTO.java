package co.edu.uniquindio.pos_resturant_app.dto.orden;

import java.time.LocalDateTime;

public record OrdenReadDTO(
        Integer idOrden,
        LocalDateTime fechaInicio,
        LocalDateTime fechaCierre,
        java.math.BigDecimal subtotal,
        java.math.BigDecimal impuestos,
        Integer numeroMesa,
        String cedulaMesero
) {}
