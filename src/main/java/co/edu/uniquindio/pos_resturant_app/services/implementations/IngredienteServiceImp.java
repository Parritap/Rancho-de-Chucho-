package co.edu.uniquindio.pos_resturant_app.services.implementations;


import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.repository.IngredienteRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UsuarioRepo;
import co.edu.uniquindio.pos_resturant_app.services.specifications.IngredienteService;
import co.edu.uniquindio.pos_resturant_app.model.Ingrediente;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class IngredienteServiceImp implements IngredienteService {

    private final IngredienteRepo ingredienteRepo;
    private final UsuarioRepo usuarioRepo;
    private final UnidadMedidaRepo unidadMedidaRepo;


    /**
     * Method to create a new Ingrediente
     *
     * @param dto
     * @return the id of the created Ingrediente.
     * @throws RecordNotFoundException if the the specified UnidadMedida in the DTO does not exist.
     */
    @Override
    public int create(IngredienteCreateDTO dto) throws Exception {
        var entity = dto.toEntity();
        entity.setUnidadMedida(unidadMedidaRepo.findById(dto.unidad_medida()).orElseThrow(() ->
                new RecordNotFoundException("UNIDAD MEDIDA")));
        return ingredienteRepo.save(entity).getId();
    }

    @Override
    public boolean update(IngredienteCreateDTO dto, int id) throws RecordNotFoundException {
        var entityOptional = ingredienteRepo.findById(id);
        if (entityOptional.isPresent()) {
            var ingrediente = entityOptional.get();
            ingrediente.setNombre(dto.nombre());
            ingrediente.setMarca(dto.marca());
            ingrediente.setPrecioCompra(dto.precioCompra());
            ingrediente.setCantidadDisponible(dto.cantidadDisponible());
            ingrediente.setUnidadMedida(unidadMedidaRepo.findById(dto.unidad_medida()).orElseThrow(() ->
                    new RecordNotFoundException("UNIDAD MEDIDA")));
            ingredienteRepo.save(ingrediente);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        var ingredienteEntity = ingredienteRepo.findById(Integer.parseInt(id));
        if (ingredienteEntity.isPresent()) {
            ingredienteRepo.delete(ingredienteEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public IngredienteCreateDTO findById(String id) throws Exception {
        return null;
    }

    @Override
    public List<IngredienteReadDTO> getAll() throws Exception {
        return ingredienteRepo.findAll().stream()
                .map(IngredienteReadDTO::new)
                .toList();
    }

    @Override
    public boolean editStock(String id, int cantidad) throws Exception {
        var ingrediente = ingredienteRepo.findById(Integer.parseInt(id)).orElseThrow(() -> new RecordNotFoundException("INGREDIENTE"));
        ingrediente.setCantidadDisponible(cantidad);
        ingredienteRepo.save(ingrediente);
        return true;
    }
}
