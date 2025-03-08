package co.edu.uniquindio.pos_resturant_app.services.implementations;


import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteCreateDTO;
import co.edu.uniquindio.pos_resturant_app.dto.ingrediente.IngredienteReadDTO;
import co.edu.uniquindio.pos_resturant_app.exceptions.CascadeEffectException;
import co.edu.uniquindio.pos_resturant_app.exceptions.DuplicatedRecordException;
import co.edu.uniquindio.pos_resturant_app.exceptions.RecordNotFoundException;
import co.edu.uniquindio.pos_resturant_app.repository.IngredienteRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UnidadMedidaRepo;
import co.edu.uniquindio.pos_resturant_app.repository.UsuarioRepo;
import co.edu.uniquindio.pos_resturant_app.repository.joints.IngredientePlatoRepo;
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
    private final IngredientePlatoRepo ingredientePlatoRepo;

    /**
     * Method to create a new Ingrediente
     *
     * @param dto
     * @return the id of the created Ingrediente.
     * @throws RecordNotFoundException   if  the specified UnidadMedida in the DTO does not exist.
     * @throws DuplicatedRecordException if an Ingrediente with the same name and brand already exists.
     */
    @Override
    public int create(IngredienteCreateDTO dto) throws RecordNotFoundException, DuplicatedRecordException {
        var entity = dto.toEntity();
        entity.setUnidadMedida(unidadMedidaRepo.findById(dto.unidad_medida()).orElseThrow(() ->
                new RecordNotFoundException("UNIDAD MEDIDA")));

        var copyOptional = ingredienteRepo.findFirstByNombreAndMarca(dto.nombre(), dto.marca());
        if (copyOptional.isPresent()) {
            throw new DuplicatedRecordException(
                    "Otra entidad con el mismo nombre y marca ya existe \nIngrediente ->\n" + copyOptional.get().toJSON());
        }
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
public boolean delete(Integer id) throws CascadeEffectException, RecordNotFoundException {
    if (ingredientePlatoRepo.existsByIngredienteId(id)) {
        throw new CascadeEffectException("No se puede eliminar el ingrediente porque otras tablas dependen de este.");
    }

    var ingrediente = ingredienteRepo.findById(id).orElseThrow(() ->
        new RecordNotFoundException("Ingrediente con ID " + id + " no encontrado")
    );

    ingredienteRepo.delete(ingrediente);
    return true;
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
