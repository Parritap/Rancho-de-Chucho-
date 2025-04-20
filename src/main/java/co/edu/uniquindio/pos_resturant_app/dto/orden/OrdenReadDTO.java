package co.edu.uniquindio.pos_resturant_app.dto.orden;

import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;

import java.time.LocalDateTime;
import java.util.List;

public record OrdenReadDTO(
        Integer idOrden,
        LocalDateTime fechaInicio,
        java.math.BigDecimal subtotal,
        java.math.BigDecimal impuestos,
        Integer idMesa,
        String cedulaMesero,
        EstadoOrden estadoOrden,
        List<PlatoOrdenadoDTO> platos
) {}
