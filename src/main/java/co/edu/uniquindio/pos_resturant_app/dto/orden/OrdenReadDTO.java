package co.edu.uniquindio.pos_resturant_app.dto.orden;

import co.edu.uniquindio.pos_resturant_app.model.Orden;
import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.model.joints.OrdenPlato;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record OrdenReadDTO(
        Integer idOrden,
        LocalDateTime fechaInicio,
        BigDecimal subtotal,
        BigDecimal impuestos,
        Integer idMesa,
        String cedulaMesero,
        EstadoOrden estadoOrden,
        List<PlatoOrdenadoDTO> platos
) {

    public static OrdenReadDTO toDTO(Orden orden, List<OrdenPlato> platos) {
        return new OrdenReadDTO(
                orden.getIdOrden(),
                orden.getFechaInicio(),
                orden.getSubtotal(),
                orden.getImpuestos(),
                orden.getMesa().getId(),
                orden.getMesero().getCedula(),
                orden.getEstado(),
                platos.stream().map(op ->
                        new PlatoOrdenadoDTO(
                          
                        )).collect(Collectors.toList())
        );
    }
}
