package co.edu.uniquindio.pos_resturant_app.services.implementations;

import co.edu.uniquindio.pos_resturant_app.dto.unidadMedida.UnidadMedidaCreateDTO;
import co.edu.uniquindio.pos_resturant_app.model.UnidadMedida;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.UnidadMedidaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UnidadMedidaServiceImp implements UnidadMedidaService {

    private final UnidadMedidaRepo unidadMedidaRepo;


    /**
     * Method to create a new UnidadMedida
     * @param dto Containing [notacion, nombre]
     * @return false if the UnidadMedida already exists meaning it was NOT created, true if the UnidadMedida was created successfully
     *
     */
    @Override
    public boolean create(UnidadMedidaCreateDTO dto) throws Exception {
        var entityOptional = unidadMedidaRepo.findById(dto.notacion());
        if (entityOptional.isPresent()) return false;
        var entity = dto.toEntity();
        unidadMedidaRepo.save(entity);
        return true;
    }

    @Override
    public boolean create(UnidadMedida entity) throws Exception {
        var optional = unidadMedidaRepo.findById(entity.getNotacion());
        if (optional.isPresent()) return false;
        unidadMedidaRepo.save(entity);
        return true;
    }
}
