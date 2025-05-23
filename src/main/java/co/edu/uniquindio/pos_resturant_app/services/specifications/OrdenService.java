package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.joints.OrdenPlatoDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.orden.OrdenReadDTO;
import co.edu.uniquindio.pos_resturant_app.model.enums.EstadoOrden;
import co.edu.uniquindio.pos_resturant_app.response.OrdenResponseSet;

import java.util.List;

public interface OrdenService {

    Integer openOrden(OrdenCreateDTO dto);

    Boolean closeOrden (Integer idOrden);

    boolean editQuantityDetail (OrdenPlatoDTO ordenPlatoDTO);

    boolean editEstadoOrden (Integer idOrden, EstadoOrden estado);

    List<OrdenReadDTO> getOrdenes (int page, int pageSize );
}
