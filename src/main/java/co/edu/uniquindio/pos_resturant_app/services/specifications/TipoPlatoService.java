package co.edu.uniquindio.pos_resturant_app.services.specifications;

import co.edu.uniquindio.pos_resturant_app.dto.tipoPlato.TipoPlatoReadDTO;

import java.util.List;

public interface TipoPlatoService {

    List<TipoPlatoReadDTO> listarTiposPlato() throws Exception;
}
