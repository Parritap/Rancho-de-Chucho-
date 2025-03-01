package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.unidadMedida.UnidadMedidaCreateDTO;
import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;

public interface UnidadMedidaService {

    public boolean create(UnidadMedidaCreateDTO dto) throws Exception;
    public boolean create(UnidadMedida entity) throws Exception;

}
